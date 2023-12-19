package screen.main.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import navigation.main.settings.ThemeSelectionScreenComponent

@Composable
fun ThemeSelectionScreen(
    component: ThemeSelectionScreenComponent
) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            navigationIcon = {
                IconButton(onClick = component::onBack) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Navigate back")
                }
            },
            title = { Text("Settings") }
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text("Theme selection")
    }
}
