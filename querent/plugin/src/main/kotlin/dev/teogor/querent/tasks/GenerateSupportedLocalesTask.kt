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

import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.NOTHING
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.TypeVariableName
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.asTypeName
import dev.teogor.querent.api.gradle.BaseTask
import dev.teogor.querent.utils.Country
import dev.teogor.querent.utils.Language
import dev.teogor.querent.utils.LanguageDialect
import dev.teogor.querent.utils.Linguistic
import dev.teogor.querent.utils.convertToUnicodeLanguageTag
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.TaskAction
import java.util.Locale

/**
 * Generates a Kotlin source file `SupportedDialects.kt` that contains a map of language tags to
 * language names (both endonyms and exonyms). Debug and Release versions are added to their respective
 * source sets.
 */
abstract class GenerateSupportedLocalesTask : BaseTask() {

  private val SupportedDialects by lazy { "dev.teogor.ceres.core.register" type "SupportedDialects" }

  @get:InputFile
  abstract val languageListInput: RegularFileProperty

  @get:Input
  abstract val defaultLocaleProperty: Property<String>

  @TaskAction
  fun taskAction() {
    val outputClassName = "SupportedDialects"

    val languageSets = languageListInput.get().asFile.readLines().map { it.split(',') }.toSet()
    val languageTags = languageSets.map { it.first() }
    val localeMap = languageTags.map { Locale.forLanguageTag(it) }

    val supportedLanguagesList = languageTags.groupBy { tag ->
      tag.split("-")[0]
    }.keys
    val supportedLanguages = supportedLanguages(supportedLanguagesList.toList())
    val tagsList = tagsList(languageTags)
    val endonymsMap = endonymsMap(languageSets)
    val exonymsMaps = exonymsMaps(localeMap)
    val defaultLocaleLanguageFunc = defaultLocaleLanguageFunc(defaultLocaleProperty.get())
    val defaultLocaleFunc = defaultLocaleFunc(defaultLocaleProperty.get())
    val supportedLanguagesFunc = supportedLanguagesFunc(supportedLanguages)
    val tagsFunc = supportedDialectsFunc(tagsList)
    val endonymsFunc = endonymsFunc(endonymsMap)
    val errorNotFoundString = errorNotFoundString()
    val exonymsFromTagFunc = exonymsFromTagFunc(exonymsMaps, errorNotFoundString)
    val exonymsFromLocaleFunc = exonymsFromLocaleFunc(exonymsMaps, errorNotFoundString)
    val countryCodesForLanguageFunc = countryCodesForLanguageFunc(tagsList)
    val countryNamesForLanguageFunc = countryNamesForLanguage(countryCodesForLanguageFunc)
    val hasMultipleCountriesForLanguageFunc =
      hasMultipleCountriesForLanguage(countryCodesForLanguageFunc)
    val currentLocaleFunc = currentLocale()
    val isLanguageSelectedFunc = isLanguageSelected()

    fileSpec(outputClassName) {
      addType(
        TypeSpec.classBuilder(outputClassName)
          .addKdoc(
            """Generated class containing the locales supported by your project in the form of list and maps.
                        |Language tags and their corresponding names (endonyms and exonyms) are retrievable through public functions.
            """.trimMargin(),
          )
          .addSuperinterface(SupportedDialects)
          .addProperties(
            listOf(
              supportedLanguages,
              tagsList,
              endonymsMap,
              exonymsMaps,
            ),
          ).addFunctions(
            listOf(
              errorNotFoundString,
              defaultLocaleLanguageFunc,
              defaultLocaleFunc,
              supportedLanguagesFunc,
              tagsFunc,
              endonymsFunc,
              exonymsFromTagFunc,
              exonymsFromLocaleFunc,
              countryCodesForLanguageFunc,
              countryNamesForLanguageFunc,
              hasMultipleCountriesForLanguageFunc,
              currentLocaleFunc,
              isLanguageSelectedFunc,
            ),
          ).build(),
      )
    }

    val enableLanguageGen = false
    if (enableLanguageGen) {
      fileSpec("Linguistic") {
        addType(
          TypeSpec.interfaceBuilder("Linguistic")
            .build(),
        )
      }

      fileSpec("Language") {
        TypeSpec.enumBuilder("Language")
          .addSuperinterface(type.Linguistic)
          .apply {
            primaryConstructor(
              FunSpec.constructorBuilder()
                .addParameter("code", String::class)
                .build(),
            )
            addProperty(
              PropertySpec.builder("code", String::class, KModifier.PUBLIC)
                .initializer("code")
                .build(),
            )
            val countries = Locale.getISOLanguages()
            countries.forEach { code ->
              // Get the language name from the locale
              val locale = Locale(code)
              val languageName = locale.displayLanguage
              val filteredName = languageName.replaceUnknownWithSimilarChar()
              addEnumConstant(
                filteredName,
                TypeSpec.anonymousClassBuilder()
                  .addSuperclassConstructorParameter("%S", code)
                  .build(),
              )
            }
          }
          .build()
          .also { addType(it) }
      }

      fileSpec("Country") {
        TypeSpec.enumBuilder("Country")
          .addSuperinterface(type.Linguistic)
          .apply {
            primaryConstructor(
              FunSpec.constructorBuilder()
                .addParameter("code", String::class)
                .build(),
            )
            addProperty(
              PropertySpec.builder("code", String::class, KModifier.PUBLIC)
                .initializer("code")
                .build(),
            )
            val countries = Locale.getISOCountries()
            countries.forEach { code ->
              // Get the country name from the locale
              val locale = Locale("", code)
              val countryName = locale.displayCountry
              val filteredName = countryName.replaceUnknownWithSimilarChar()
              addEnumConstant(
                filteredName,
                TypeSpec.anonymousClassBuilder()
                  .addSuperclassConstructorParameter("%S", code)
                  .build(),
              )
            }
          }
          .build()
          .also { addType(it) }
      }

      fileSpec("LanguageDialect") {
        addType(
          TypeSpec.classBuilder("LanguageDialect")
            .addModifiers(KModifier.DATA)
            .primaryConstructor(
              FunSpec.constructorBuilder()
                .addParameter("country", type.Country)
                .addParameter("language", type.Language)
                .build(),
            )
            .addSuperinterface(type.Linguistic)
            .addProperties(
              listOf(
                PropertySpec.builder("country", type.Country, KModifier.PUBLIC)
                  .initializer("country")
                  .build(),
                PropertySpec.builder("language", type.Language, KModifier.PUBLIC)
                  .initializer("language")
                  .build(),
              ),
            )
            .build(),
        )
      }

      fileSpec("LocaleExtensions") {
        addFunction(
          FunSpec.builder("localize")
            .addTypeVariable(TypeVariableName.Companion.invoke("L : Language"))
            .addTypeVariable(TypeVariableName.Companion.invoke("C : Country"))
            .receiver(TypeVariableName.Companion.invoke("L"))
            .addParameter(
              ParameterSpec.builder("country", TypeVariableName.Companion.invoke("C")).build(),
            )
            .returns(type.LanguageDialect)
            .addModifiers(KModifier.INFIX)
            .addStatement("return LanguageDialect(country = country, language = this)").build(),
        )

        addFunction(
          FunSpec.builder("obtainLanguageTag")
            .receiver(type.Linguistic)
            .returns(String::class)
            .addStatement(
              """
            return when(this) {
              is Country -> %T("-", this.code.uppercase()).toLanguageTag()
              is Language -> %T(this.code).toLanguageTag()
              is LanguageDialect -> this.toLocale().toLanguageTag()
              else -> %T.getDefault().toLanguageTag()
            }
              """.trimIndent(),
              Locale::class,
              Locale::class,
              Locale::class,
            )
            .build(),
        )

        addFunction(
          FunSpec.builder("toLocale")
            .receiver(type.LanguageDialect)
            .returns(Locale::class)
            .addStatement(
              "return Locale(language.code, country.code)",
            )
            .build(),
        )
      }
    }
    logger.info("$outputClassName.kt output to ${outputDir.get().asFile}")
  }

  private fun String.replaceUnknownWithSimilarChar() = this
    .replace("[\\s&'\\-()]".toRegex(), "")
    .replace("[^A-Za-z]".toRegex()) { result ->
      val char = result.value[0]
      when (char.uppercaseChar()) {
        'Å' -> if (char.isUpperCase()) "A" else "a"
        'Ä' -> if (char.isUpperCase()) "A" else "a"
        'Ã' -> if (char.isUpperCase()) "A" else "a"
        'Í' -> if (char.isUpperCase()) "I" else "i"
        'Ô' -> if (char.isUpperCase()) "O" else "o"
        'Ö' -> if (char.isUpperCase()) "O" else "o"
        'ß' -> if (char.isUpperCase()) "S" else "s"
        'É' -> if (char.isUpperCase()) "E" else "e"
        'Ü' -> if (char.isUpperCase()) "U" else "u"
        'Ç' -> if (char.isUpperCase()) "C" else "c"
        else -> char.toString()
      }
    }

  private fun supportedLanguages(languages: List<String>) = PropertySpec.builder(
    name = "supportedLanguages",
    type = List::class.parameterizedBy(String::class),
  ).initializer(
    "listOf(\n%L\n)",
    languages.chunked(5)
      .joinToString("\n") {
        it.joinToString(" ") { code ->
          "\"${code}\","
        }
      },
  ).addModifiers(
    KModifier.PRIVATE,
  ).build()

  private fun tagsList(languageTags: List<String>) = PropertySpec.builder(
    name = "supportedDialects",
    type = List::class.parameterizedBy(String::class),
  ).initializer(
    "listOf(\n%L\n)",
    languageTags.joinToString(",\n") { "\"$it\"" },
  ).addModifiers(
    KModifier.PRIVATE,
  ).build()

  private fun endonymsMap(languageSets: Set<List<String>>) = PropertySpec.builder(
    name = "endonyms",
    type = Map::class.parameterizedBy(String::class, String::class),
  ).initializer(
    "mapOf(\n%L\n)",
    languageSets.joinToString(",\n") {
      "\"${it.first()}\" to \"${it.last()}\""
    },
  ).addModifiers(
    KModifier.PRIVATE,
  ).build()

  private fun exonymsMaps(locales: List<Locale>) = PropertySpec.builder(
    name = "exonyms",
    type = Map::class.asClassName().parameterizedBy(
      String::class.asTypeName(),
      Map::class.parameterizedBy(String::class, String::class),
    ),
  ).initializer(
    "mapOf(\n%L\n)",
    generateExonymMapString(locales),
  ).addModifiers(
    KModifier.PRIVATE,
  ).build()

  private fun generateExonymMapString(locales: List<Locale>) = locales.map { loc ->
    val exonyms = locales.map { it.toLanguageTag() }
      .zip(locales.map { it.getDisplayName(loc) })

    "\"${loc.toLanguageTag()}\" to mapOf(\n${
      exonyms.joinToString(",\n") { "\"${it.first}\" to \"${it.second}\"" }
    }\n)"
  }.joinToString(",\n")

  private fun defaultLocaleLanguageFunc(def: String) = FunSpec.builder("getDefaultLocaleLanguage")
    .returns(String::class)
    .addModifiers(KModifier.OVERRIDE)
    .addStatement(
      "return %S",
      def.convertToUnicodeLanguageTag(),
    )
    .build()

  private fun defaultLocaleFunc(def: String) = FunSpec.builder("getDefaultLocale")
    .returns(Locale::class)
    .addModifiers(KModifier.OVERRIDE)
    .addStatement(
      "return %T.forLanguageTag(%S)",
      Locale::class,
      def.convertToUnicodeLanguageTag(),
    )
    .build()

  private fun supportedLanguagesFunc(property: PropertySpec) =
    FunSpec.builder("getSupportedLanguages")
      .addKdoc(
        """
      Retrieves a map of supported languages and their corresponding names by their language code.
      The returned map contains language codes as keys and their respective language names as values.

      @return A [Map] where keys represent the language codes and values indicate the corresponding language names.
        """.trimIndent(),
      )
      .addModifiers(KModifier.OVERRIDE)
      .addStatement("return %N", property)
      .returns(List::class.parameterizedBy(String::class))
      .build()

  private fun countryCodesForLanguageFunc(property: PropertySpec) =
    FunSpec.builder("getCountryCodesForLanguage")
      .addParameter("languageCode", String::class)
      .addModifiers(KModifier.OVERRIDE)
      .returns(List::class.parameterizedBy(String::class))
      .addStatement("return %N.filter { it.startsWith(languageCode) }", property)
      .build()

  private fun countryNamesForLanguage(property: FunSpec) =
    FunSpec.builder("getCountryNamesForLanguage")
      .addParameter("languageCode", String::class)
      .addModifiers(KModifier.OVERRIDE)
      .returns(List::class.parameterizedBy(String::class))
      .addStatement(
        "return %N(languageCode).mapNotNull { endonyms[it] }",
        property,
      )
      .build()

  private fun hasMultipleCountriesForLanguage(property: FunSpec) =
    FunSpec.builder("hasMultipleCountriesForLanguage")
      .addParameter("languageCode", String::class)
      .addModifiers(KModifier.OVERRIDE)
      .returns(Boolean::class)
      .addStatement("return %N(languageCode).size > 1", property)
      .build()

  private fun currentLocale() = FunSpec.builder("getCurrentLocale")
    .returns(Locale::class)
    .addModifiers(KModifier.OVERRIDE)
    .addStatement("return %T.getDefault()", Locale::class)
    .build()

  private fun isLanguageSelected() = FunSpec.builder("isLanguageSelected")
    .addParameter("languageCode", String::class)
    .returns(Boolean::class)
    .addModifiers(KModifier.OVERRIDE)
    .addStatement("val currentLocale = %T.getDefault().toLanguageTag()", Locale::class)
    .addStatement("return languageCode == currentLocale || currentLocale.startsWith(languageCode)")
    .build()

  private fun supportedDialectsFunc(property: PropertySpec) = FunSpec.builder(
    "getSupportedDialects",
  )
    .addKdoc("@returns List of dialects supported by your project.")
    .addStatement("return %N", property)
    .addModifiers(KModifier.OVERRIDE)
    .returns(List::class.asClassName().parameterizedBy(String::class.asTypeName()))
    .build()

  private fun endonymsFunc(property: PropertySpec) = FunSpec.builder("getEndonyms")
    .addKdoc(
      """@returns Map of language tags and their written endoynms.
                |(Endonyms are the preferred name of a language as written in that language.)
                |
      """.trimMargin(),
    )
    .addStatement("return %N", property)
    .addModifiers(KModifier.OVERRIDE)
    .returns(Map::class.parameterizedBy(String::class, String::class))
    .build()

  private fun exonymsFromTagFunc(property: PropertySpec, error: FunSpec): FunSpec {
    val langTagParam = ParameterSpec.builder("languageTag", String::class)
      .build()

    return FunSpec.builder("getExonyms")
      .addKdoc(
        """@param ${langTagParam.name} a Unicode-formatted language tag in [String] form such as "en-US".
                |@returns Map of language tags and their written exoynms according to the resolved ${langTagParam.name}.
                |(Exonyms are the name of a language written in another language.)
                |
        """.trimMargin(),
      )
      .addParameter(langTagParam)
      .addStatement(
        "return %N[%N] ?: %N[getDefaultLocaleLanguage()] ?: %N(languageTag)",
        property,
        langTagParam,
        property,
        error,
      )
      // TODO do we need to throw error?
      //  .addStatement("return %N[%N] ?: throw %N(languageTag)", property, langTagParam, error)
      .addModifiers(KModifier.OVERRIDE)
      .returns(
        Map::class.asClassName().parameterizedBy(
          String::class.asTypeName(),
          String::class.asTypeName(),
        ),
      )
      .build()
  }

  private fun exonymsFromLocaleFunc(property: PropertySpec, error: FunSpec): FunSpec {
    val localeParam = ParameterSpec.builder("locale", Locale::class)
      .build()

    return FunSpec.builder("getExonyms")
      .addKdoc(
        """@param ${localeParam.name} a Java [Locale] object.
                |@returns Map of language tags and their written exoynms according to the resolved ${localeParam.name}.
                |(Exonyms are the name of a language written in another language.)
                |
        """.trimMargin(),
      )
      .addParameter(localeParam)
      .addModifiers(KModifier.OVERRIDE)
      .addStatement(
        "return %N[%N.toLanguageTag()] ?: %N[getDefaultLocaleLanguage()] ?: %N(locale.toLanguageTag())",
        property,
        localeParam,
        property,
        error,
      )
      // TODO do we need to throw error?
      //  .addStatement("return %N[%N.toLanguageTag()] ?: throw %N(locale.toLanguageTag())", property, localeParam, error)
      .returns(
        Map::class.asClassName().parameterizedBy(
          String::class.asTypeName(),
          String::class.asTypeName(),
        ),
      )
      .build()
  }

  private fun errorNotFoundString(): FunSpec {
    val message = "\$errorMessage"
    val exceptionMessage = """
  |The specified language tag ("$message") was not located in your supported locales.
  |Ensure it's included within the 'supportedLanguages' property in your Gradle setup.
  |
  |```kt
  |buildOptions {
  |  buildFeatures {
  |    addSupportedLanguages {
  |      listOf(
  |        // add it here
  |        "$message",
  |      )
  |    }
  |  }
  |}
  |```
    """.trimMargin()
    return FunSpec.builder("errorTagNotFound")
      .addParameter(ParameterSpec.builder("errorMessage", String::class).build())
      .returns(NOTHING)
      .addStatement(
        "throw %T(%P)",
        NoSuchElementException::class,
        exceptionMessage,
      )
      .addModifiers(KModifier.PRIVATE)
      .build()
  }
}
