package presentation.screen.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import presentation.navigation.main.feed.FeedComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(
    component: FeedComponent
) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("Feed") },
            actions = {
                IconButton(onClick = component::onLogout) {
                    Icon(imageVector = Icons.Default.ExitToApp, contentDescription = "Logout")
                }
            }
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Feed Screen")
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = {
                component.onDetails(id = 1)
            }) {
                Text("Details")
            }
        }
    }
}
