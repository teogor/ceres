/*
 * Copyright 2023 teogor (Teodor Grigor)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.teogor.querent.tasks

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.STAR
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.TypeVariableName
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.asTypeName
import dev.teogor.querent.api.gradle.BaseTask
import dev.teogor.querent.utils.Composable
import dev.teogor.querent.utils.QuantityStringResource
import dev.teogor.querent.utils.QuantityStrings
import dev.teogor.querent.utils.R
import dev.teogor.querent.utils.Resources
import dev.teogor.querent.utils.StringArray
import dev.teogor.querent.utils.StringArrayResource
import dev.teogor.querent.utils.StringResource
import dev.teogor.querent.utils.StringValue
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.TaskAction
import java.util.Date
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

/**
 * Generates a `locale_config.xml` file to be referenced in an Android project Manifest for
 * configuring application locale support. Debug and Release versions are added to their respective
 * source sets.
 */
abstract class GenerateValuesTask : BaseTask() {
  private val ResourceKey by lazy { packageName.get() type "ResourceKey" }
  private val ResourceType by lazy { packageName.get() type "ResourceType" }

  @get:InputFile
  abstract val valuesListInput: RegularFileProperty

  @TaskAction
  fun taskAction() {
    val file = valuesListInput.get().asFile
    val resources = file.readLines().mapNotNull { checkResourceType(it) }
    if (resources.isEmpty()) {
      return
    }

    val moduleNameCase = moduleName.get().toCamelCase()
    val outputClassName = "Resources"

    // Create a Kotlin Poet object
    val resourceObject = TypeSpec.objectBuilder(outputClassName)

    // Add properties to the object based on the identified resources
    resources.filterIsInstance<StringValue>()
      .forEach { element ->
        if (element.placeholders.isEmpty()) {
          resourceObject.addProperty(
            PropertySpec.builder(element.id.snakeToCamelCase(), String::class)
              .getter(
                FunSpec.getterBuilder()
                  .addAnnotation(Composable)
                  .addStatement("return %T.${element.id.idToName()}.asResource()", ResourceKey)
                  .build(),
              )
              .build(),
          )
        } else {
          val arguments = if (element.args.size == element.placeholders.size) {
            element.args.joinToString(",") { it }
          } else {
            (0 until element.placeholders.size).joinToString(",") { "arg$it" }
          }

          resourceObject.addFunction(
            FunSpec.builder(element.id.snakeToCamelCase())
              .returns(String::class)
              .addAnnotation(Composable)
              .apply {
                if (element.args.size == element.placeholders.size) {
                  element.args.forEachIndexed { index, arg ->
                    addParameter(
                      ParameterSpec.builder(arg, getArgumentType(element.placeholders[index]))
                        .build(),
                    )
                  }
                } else {
                  repeat(element.placeholders.size) {
                    addParameter(
                      ParameterSpec.builder("arg$it", getArgumentType(element.placeholders[it]))
                        .build(),
                    )
                  }
                }
              }
              .addStatement(
                "return %T.${element.id.idToName()}.asResource(args = arrayOf($arguments))",
                ResourceKey,
              )
              .build(),
          )
        }
      }
    resources.filterIsInstance<StringArray>()
      .forEach { element ->
        resourceObject.addProperty(
          PropertySpec.builder(
            element.id.snakeToCamelCase(),
            Array::class.parameterizedBy(String::class),
          )
            .getter(
              FunSpec.getterBuilder()
                .addAnnotation(Composable)
                .addStatement("return %T.${element.id.idToName()}.asResource()", ResourceKey)
                .build(),
            )
            .build(),
        )
      }
    resources.filterIsInstance<QuantityStrings>()
      .forEach { element ->
        resourceObject.addProperty(
          PropertySpec.builder(element.id.snakeToCamelCase(), String::class)
            .getter(
              FunSpec.getterBuilder()
                .addAnnotation(Composable)
                .addStatement("return %T.${element.id.idToName()}.asResource()", ResourceKey)
                .build(),
            )
            .build(),
        )

        resourceObject.addFunction(
          FunSpec.builder(element.id.snakeToCamelCase())
            .returns(String::class)
            .addAnnotation(Composable)
            .addParameter(
              ParameterSpec.builder("quantity", Int::class)
                .defaultValue("%L", 0)
                .build(),
            )
            .addParameter("args", Any::class, KModifier.VARARG)
            .addStatement(
              "return %T.${element.id.idToName()}.asResource(quantity = quantity, args)",
              ResourceKey,
            )
            .build(),
        )
      }

    // Create a file and write the object to it
    FileSpec.builder(packageName.get(), outputClassName)
      .addType(resourceObject.build())
      .build()
      .generate()

    generateResourceType()
    generateResourceIds(resources)
    generateResourceKey(resources)
  }

  private fun generateResourceType() {
    val resourcesIdName = "ResourceType"

    val acceptedTypes = listOf(
      "String",
      "Array",
      "Plurals",
    ).sorted()

    val enumType = TypeSpec.enumBuilder(resourcesIdName)
      .apply {
        acceptedTypes.forEach {
          addEnumConstant(it)
        }
      }
      .build()

    FileSpec.builder(packageName.get(), resourcesIdName)
      .addType(enumType)
      .build()
      .generate()
  }

  private fun generateResourceIds(resources: List<Resources>) {
    val resourcesIdName = "ResourceIds"

    // Create a file and write the object to it
    FileSpec.builder(packageName.get(), resourcesIdName)
      .apply {
        resources.filterIsInstance<StringValue>()
          .forEach { element ->
            val property = PropertySpec.builder("${element.id.snakeToCamelCase()}Id", Int::class)
              .initializer(
                "%T.${element.id.idToName()}",
                ResourceKey,
              )
              .delegate("%T.${element.id.idToName()}", ResourceKey)
              .build()
            addProperty(property)
          }
        resources.filterIsInstance<StringArray>()
          .forEach { element ->
            val property = PropertySpec.builder("${element.id.snakeToCamelCase()}Id", Int::class)
              .initializer(
                "%T.${element.id.idToName()}",
                ResourceKey,
              )
              .delegate("%T.${element.id.idToName()}", ResourceKey)
              .build()
            addProperty(property)
          }
        resources.filterIsInstance<QuantityStrings>()
          .forEach { element ->
            val property = PropertySpec.builder("${element.id.snakeToCamelCase()}Id", Int::class)
              .initializer(
                "%T.${element.id.idToName()}",
                ResourceKey,
              )
              .delegate("%T.${element.id.idToName()}", ResourceKey)
              .build()
            addProperty(property)
          }
      }
      .build()
      .generate()
  }

  private fun generateResourceKey(resources: List<Resources>) {
    val resourcesIdName = "ResourceKey"
    val enumTypeSpec = TypeSpec.enumBuilder(resourcesIdName)
      .primaryConstructor(
        FunSpec.constructorBuilder()
          .addParameter("id", Int::class)
          .addParameter("type", ResourceType)
          .build(),
      )
      .addProperty(
        PropertySpec.builder("id", Int::class)
          .initializer("id")
          .build(),
      )
      .addProperty(
        PropertySpec.builder("type", ResourceType)
          .initializer("type")
          .build(),
      )

    resources.sortedBy { resource ->
      when (resource) {
        is StringValue -> resource.id
        is StringArray -> resource.id
        is QuantityStrings -> resource.id
        else -> resource.toString()
      }
    }.forEach {
      when (it) {
        is StringValue -> {
          val enumValueName = it.id.idToName()
          enumTypeSpec.addEnumConstant(
            enumValueName,
            TypeSpec.anonymousClassBuilder()
              .addSuperclassConstructorParameter(
                "%T.string.${it.id}, %T.String",
                type.R,
                ResourceType,
              )
              .build(),
          )
        }

        is StringArray -> {
          val enumValueName = it.id.idToName()
          enumTypeSpec.addEnumConstant(
            enumValueName,
            TypeSpec.anonymousClassBuilder()
              .addSuperclassConstructorParameter("R.array.${it.id}, %T.Array", ResourceType)
              .build(),
          )
        }

        is QuantityStrings -> {
          val enumValueName = it.id.idToName()
          enumTypeSpec.addEnumConstant(
            enumValueName,
            TypeSpec.anonymousClassBuilder()
              .addSuperclassConstructorParameter("R.plurals.${it.id}, %T.Plurals", ResourceType)
              .build(),
          )
        }
      }
    }

    val reifiedType = TypeVariableName("Type")
    val asResourceFunction = FunSpec.builder("asResource")
      .addParameter(
        ParameterSpec.builder("quantity", Int::class)
          .defaultValue("%L", 0)
          .build(),
      )
      .addParameter("args", Any::class, KModifier.VARARG)
      .addAnnotation(Composable)
      .addModifiers(KModifier.INLINE)
      .addTypeVariable(reifiedType.copy(reified = true))
      .receiver(ResourceKey)
      .returns(reifiedType)
      .beginControlFlow("return when (type)", reifiedType)
      .addStatement("%T.Array -> %T(id) as %T", ResourceType, StringArrayResource, reifiedType)
      .addStatement(
        "%T.Plurals -> %T(id, quantity, *args) as %T",
        ResourceType,
        QuantityStringResource,
        reifiedType,
      )
      .addStatement("%T.String -> %T(id, *args) as %T", ResourceType, StringResource, reifiedType)
      .endControlFlow()
      .build()

    // Create a file and write the object to it
    FileSpec.builder(packageName.get(), resourcesIdName)
      .addType(enumTypeSpec.build())
      .addFunction(
        FunSpec.builder("getValue")
          .receiver(ResourceKey)
          .addParameter("thisObj", Any::class.asTypeName().copy(nullable = true))
          .addParameter("property", KProperty::class.asClassName().parameterizedBy(STAR))
          .returns(Int::class)
          .addModifiers(KModifier.OPERATOR)
          .addStatement("return id")
          .build(),
      )
      .addFunction(asResourceFunction)
      .build()
      .generate()
  }

  private fun String.toCamelCase() = this
    .replace(Regex("[_-]([a-zA-Z])")) { result ->
      result.value.uppercase().removeRange(0..0)
    }

  private fun String.idToName() = this
    .replace(Regex("([a-z])([A-Z])"), "$1_$2")
    .uppercase()

  private fun String.snakeToCamelCase(): String {
    val formattedString = this.replace(Regex("_([a-z])")) {
      it.value.replace("_", "").uppercase()
    }
    return formattedString.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
  }

  private fun checkResourceType(line: String): Resources? {
    return when {
      line.startsWith("string") -> {
        val parts = line.split(" ")
        StringValue(parts[1], parts[2].toList(), parts[3].split(";"))
      }

      line.startsWith("array") -> StringArray(line.substringAfter(' '))
      line.startsWith("plurals") -> QuantityStrings(line.substringAfter(' '))
      else -> null
    }
  }

  private fun getArgumentType(arg: Char): KClass<out Any> {
    return when (arg) {
      's' -> String::class
      'd' -> Int::class
      'f' -> Float::class
      'b' -> Boolean::class
      't' -> Date::class
      else -> Any::class
    }
  }
}
