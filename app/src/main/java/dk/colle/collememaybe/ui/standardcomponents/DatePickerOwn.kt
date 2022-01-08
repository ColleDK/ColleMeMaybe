package dk.colle.collememaybe.ui.standardcomponents

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import dk.colle.collememaybe.util.CurrentDateFormatter
import java.text.SimpleDateFormat
import java.util.*

@ExperimentalComposeUiApi
@Composable
fun DatePickerOwn(
    dateState: MutableState<String> = remember {
        mutableStateOf("")
    },
    onChangeDate: (String) -> Unit,
    context: Context = LocalContext.current,
    mYear: () -> Int = { Calendar.getInstance().get(Calendar.YEAR) },
    mMonth: () -> Int = { Calendar.getInstance().get(Calendar.MONTH) },
    mDay: () -> Int = { Calendar.getInstance().get(Calendar.DAY_OF_MONTH) },
    dateFormatter: SimpleDateFormat = CurrentDateFormatter.DATEFORMATTER
) {
    val now = Calendar.getInstance()
    now.time = Date()

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            val cal = Calendar.getInstance()
            cal.set(year, month, dayOfMonth)
            onChangeDate(dateFormatter.format(cal.time))
        }, mYear(), mMonth(), mDay()
    )

    ClickInputField(
        textState = dateState,
        label = "Pick date",
        leadIcon = Icons.Filled.CalendarToday,
        leadIconClick = { datePickerDialog.show() },
        leadIconDesc = "Calendar icon",
        onClickText = { datePickerDialog.show() }
    )
}