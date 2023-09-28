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

package dev.teogor.ceres.ui.designsystem

import android.os.Bundle
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.ZoneId

object DateFieldInputSaver : Saver<DateFieldInput, Bundle> {
  override fun restore(value: Bundle): DateFieldInput {
    val day = value.getString("day", "")
    val month = value.getString("month", "")
    val year = value.getString("year", "")
    return DateFieldInput(day, month, year)
  }

  override fun SaverScope.save(value: DateFieldInput): Bundle {
    val bundle = Bundle()
    bundle.putString("day", value.day)
    bundle.putString("month", value.month)
    bundle.putString("year", value.year)
    return bundle
  }
}

data class DateFieldInput(
  var day: String = "",
  var month: String = "",
  var year: String = "",
)

fun DateFieldInput.toEpochMilli(): Long {
  return LocalDate.of(year.toInt(), month.toInt(), day.toInt())
    .atStartOfDay(ZoneId.systemDefault())
    .toInstant()
    .toEpochMilli()
}

@Composable
fun DateField(
  date: DateFieldInput = DateFieldInput(),
  onDateChanged: (DateFieldInput) -> Unit,
  modifier: Modifier = Modifier,
) {
  val maxDay = when (date.month.toIntOrNull()) {
    2 -> if (date.year.toIntOrNull()?.rem(4) == 0) 29 else 28
    4, 6, 9, 11 -> 30
    else -> 31
  }

  fun validateDay(value: String): Boolean {
    return value.toIntOrNull() in 1..maxDay && value.length <= 2
  }

  fun validateMonth(value: String): Boolean {
    return value.toIntOrNull() in 1..12 && value.length <= 2
  }

  fun validateYear(value: String): Boolean {
    return if (value.toIntOrNull()?.let {
        it in 1000..9999 && value.length == 4
      } == true
    ) {
      true
    } else {
      value.length < 4
    }
  }

  Row(
    modifier = modifier
      .fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    OutlinedTextField(
      value = date.day,
      onValueChange = { value ->
        if (validateDay(value)) {
          onDateChanged(date.copy(day = value))
        }
      },
      label = { Text("dd") },
      keyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Number,
        imeAction = ImeAction.Next,
      ),
      modifier = Modifier.weight(2f),
    )

    Spacer(modifier = Modifier.width(8.dp))

    OutlinedTextField(
      value = date.month,
      onValueChange = { value ->
        if (validateMonth(value)) {
          onDateChanged(date.copy(month = value))
        }
      },
      label = { Text("mm") },
      keyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Number,
        imeAction = ImeAction.Next,
      ),
      modifier = Modifier.weight(2f),
    )

    Spacer(modifier = Modifier.width(8.dp))

    OutlinedTextField(
      value = date.year,
      onValueChange = { value ->
        if (validateYear(value)) {
          onDateChanged(date.copy(year = value))
        }
      },
      label = { Text("yyyy") },
      keyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Number,
        imeAction = ImeAction.Next,
      ),
      modifier = Modifier.weight(3f),
    )
  }
}

@Deprecated("Too many arguments")
@Composable
fun DateField(
  day: String,
  month: String,
  year: String,
  onDayChange: (String) -> Unit,
  onMonthChange: (String) -> Unit,
  onYearChange: (String) -> Unit,
  modifier: Modifier = Modifier,
) {
  var date by rememberSaveable(stateSaver = DateFieldInputSaver) {
    mutableStateOf(
      DateFieldInput(),
    )
  }
  DateField(
    date = date,
    onDateChanged = {
      date = it
      with(date) {
        onDayChange(this.day)
        onMonthChange(this.month)
        onYearChange(this.year)
      }
    },
    modifier = Modifier.padding(horizontal = 6.dp),
  )
}
