import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import navigation.RootComponent
import screen.main.RootScreen

@Composable
fun App(component: RootComponent) {
    MaterialTheme {
        RootScreen(component = component)
    }
}
