package screen.main.settings

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import navigation.main.settings.SettingsScreenRootComponent


@Composable
fun SettingsRootScreen(
    component: SettingsScreenRootComponent
) {
    Surface(color = MaterialTheme.colors.background) {
        Children(
            stack = component.childStack,
            modifier = Modifier.fillMaxSize(),
            animation = stackAnimation(slide())
        ) {
            when (val child = it.instance) {
                is SettingsScreenRootComponent.Config.Settings -> SettingsScreen(component = child.component)
                is SettingsScreenRootComponent.Config.ThemeSelection -> ThemeSelectionScreen(component = child.component)
            }
        }
    }
}
