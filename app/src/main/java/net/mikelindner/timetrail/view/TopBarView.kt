package net.mikelindner.timetrail.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarView(
    title: String,
    showBackButton: Boolean,
    onBackClicked: () -> Unit = {}
) {
    var backIcon: (@Composable () -> Unit) = {}
    if (showBackButton) {
        backIcon = {
            IconButton(onClick = { onBackClicked() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null
                )
            }
        }
    }

    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = backIcon
    )
}