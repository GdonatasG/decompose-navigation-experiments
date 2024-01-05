package screen.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import navigation.RootComponent
import screen.auth.AuthScreen

@Composable
fun RootScreen(
    component: RootComponent
) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Children(
            stack = component.childStack,
            modifier = Modifier.fillMaxSize(),
            animation = stackAnimation(fade())
        ) {
            when (val child = it.instance) {
                is RootComponent.Config.Auth -> AuthScreen(child.component)
                is RootComponent.Config.Main -> MainScreen(child.component)
            }
        }
    }
}
