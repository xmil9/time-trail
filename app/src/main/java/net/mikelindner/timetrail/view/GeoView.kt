package net.mikelindner.timetrail.view

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.mikelindner.timetrail.app.AppOptions
import net.mikelindner.timetrail.domain.Date
import net.mikelindner.timetrail.domain.NullTrailsRepository
import net.mikelindner.timetrail.ui.theme.TimeTrailTheme
import net.mikelindner.timetrail.view.map.OsmMapView

private fun showError(context: Context, errText: String) {
    Toast.makeText(context, errText, Toast.LENGTH_LONG).show()
}

private fun toDate(context: Context, dateInput: String, uiLabel: String): Date? {
    try {
        return if (dateInput.isNotEmpty()) Date.fromIso8601String(dateInput) else null
    } catch (ex: Exception) {
        throw Exception("Invalid '$uiLabel' date.")
    }
}

@Composable
fun GeoView(
    vm: GeoViewModel,
    appOptions: AppOptions
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    var from = remember { mutableStateOf("") }
    var to = remember { mutableStateOf("") }
    val dateFormat = "yyyy-mm-dd"
    val fromLabel = "From"
    val toLabel = "To"

    fun refreshTimeFrame() {
        try {
            val fromDate = toDate(context, from.value, fromLabel)
            val toDate = toDate(context, to.value, toLabel)
            vm.timeConstrainedTrails(fromDate, toDate)
        } catch (ex: Exception) {
            ex.message?.let { showError (context, it) }
        }
    }

    fun clearTimeFrame() {
        from.value = ""
        to.value = ""
        vm.allTrails()
    }

    fun onDateEntered() {
        refreshTimeFrame()
        focusManager.clearFocus()
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
        ) {
            Row(
                modifier = Modifier
                    .weight(1.1f),  // A little extra space because 'From' is longer than 'To'.
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 12.dp, end = 4.dp, top = 4.dp),
                    text = fromLabel
                )
                VertSizeableTextField(
                    value = from.value,
                    placeholderValue = dateFormat,
                    onValueChange = {
                        from.value = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                        .padding(end = 0.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = { onDateEntered() }
                    ),
                )
            }

            Row(
                modifier = Modifier
                    .weight(1f),
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 8.dp, end = 4.dp, top = 4.dp),
                    text = toLabel
                )
                VertSizeableTextField(
                    value = to.value,
                    onValueChange = { to.value = it },
                    placeholderValue = dateFormat,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                        .padding(end = 8.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = { onDateEntered() }
                    ),
                )
            }

            IconButton(
                modifier = Modifier
                    .padding(top = 3.dp, end = 4.dp)
                    .size(24.dp)
                    .scale(1.0f),
                onClick = {
                    refreshTimeFrame()
                }) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
            }

            IconButton(
                modifier = Modifier
                    .padding(top = 3.dp, end = 8.dp)
                    .size(24.dp)
                    .scale(1.0f),
                onClick = {
                    clearTimeFrame()
                }) {
                Icon(imageVector = Icons.Default.Clear, contentDescription = null)
            }
        }

        OsmMapView(
            Modifier
                .fillMaxSize()
                .padding(top = 12.dp),
            vm = vm,
            appOptions = appOptions
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GeoViewPreview() {
    TimeTrailTheme {
        val context = LocalContext.current
        val vm = GeoViewModel(NullTrailsRepository())
        GeoView(
            vm,
            AppOptions()
        )
    }
}
