
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import navigation.RootScreenComponent
import screen.main.RootScreen

@Composable
fun App(component: RootScreenComponent) {
    MaterialTheme {
        RootScreen(component = component)
    }
}
