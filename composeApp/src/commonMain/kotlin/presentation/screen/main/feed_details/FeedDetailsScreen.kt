package presentation.screen.main.feed_details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import presentation.navigation.main.feed_details.FeedDetailsComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedDetailsScreen(component: FeedDetailsComponent) {
    val count by component.count.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            navigationIcon = {
                IconButton(onClick = component::onBack) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Navigate back")
                }
            },
            title = { Text("Feed Details") }
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text("count: $count")
    }
}
