import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import presentation.navigation.RootComponent
import presentation.screen.main.RootScreen

@Composable
fun App(component: RootComponent) {
    MaterialTheme {
        RootScreen(component = component)
    }
}
