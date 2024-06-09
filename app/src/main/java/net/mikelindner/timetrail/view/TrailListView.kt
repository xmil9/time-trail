package net.mikelindner.timetrail.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import net.mikelindner.timetrail.app.AppScreen
import net.mikelindner.timetrail.domain.NullTrailsRepository
import net.mikelindner.timetrail.domain.Trail
import net.mikelindner.timetrail.domain.TrailState
import net.mikelindner.timetrail.ui.theme.TimeTrailTheme

@Composable
fun TrailListView(
    vm: TrailListViewModel,
    navBack: () -> Unit,
    bottomBar: @Composable () -> Unit
) {

    Scaffold(
        bottomBar = bottomBar,
        topBar = {
            TopBarView(
                title = AppScreen.TrailList.title,
                showBackButton = true
            ) { navBack() }
        },
    ) { padding ->

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.Top,
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, end = 16.dp)
            ) {
                items(vm.trails.value) { trail ->
                    TrailItem(
                        trail,
                        vm.isSelected(trail),
                        onClick = {},
                        onCheckedChanged = { checkedTrail, checked ->
                            vm.select(checkedTrail, checked)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun TrailItem(
    trail: Trail,
    isSelected: Boolean,
    onClick: (trailId: Long) -> Unit,
    onCheckedChanged: (trail: Trail, checked: Boolean) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 2.dp, top = 2.dp, end = 2.dp)
            .clickable { onClick(trail.id) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Checkbox(
                checked = isSelected,
                onCheckedChange = { checked -> onCheckedChanged(trail, checked) },
            )
            Text(
                text = trail.name,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TrailListViewPreview() {
    TimeTrailTheme {
        val context = LocalContext.current
        TrailListView(
            TrailListViewModel(TrailState(NullTrailsRepository())),
            {},
            makeBottomBar(AppScreen.TrailList, NavHostController(context))
        )
    }
}
