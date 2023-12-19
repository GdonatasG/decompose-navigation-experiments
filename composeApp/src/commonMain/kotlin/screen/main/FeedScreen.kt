package screen.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import navigation.main.feed.FeedScreenComponent

@Composable
fun FeedScreen(
    component: FeedScreenComponent
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
