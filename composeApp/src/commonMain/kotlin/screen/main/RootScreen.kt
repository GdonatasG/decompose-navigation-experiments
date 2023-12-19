package screen.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import navigation.RootScreenComponent
import screen.auth.AuthScreen

@Composable
fun RootScreen(
    component: RootScreenComponent
) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
        Children(
            stack = component.childStack,
            modifier = Modifier.fillMaxSize(),
            animation = stackAnimation(fade())
        ) {
            when (val child = it.instance) {
                is RootScreenComponent.Config.Auth -> AuthScreen(child.component)
                is RootScreenComponent.Config.Main -> MainScreen(child.component)
            }
        }
    }
}
