package presentation.screen.main.settings

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import presentation.navigation.main.settings.SettingsRootComponent


@Composable
fun SettingsRootScreen(
    component: SettingsRootComponent
) {
    Surface(color = MaterialTheme.colorScheme.background) {
        Children(
            stack = component.childStack,
            modifier = Modifier.fillMaxSize(),
            animation = stackAnimation(slide())
        ) {
            when (val child = it.instance) {
                is SettingsRootComponent.Config.Settings -> SettingsScreen(component = child.component)
                is SettingsRootComponent.Config.ThemeSelection -> ThemeSelectionScreen(component = child.component)
            }
        }
    }
}
