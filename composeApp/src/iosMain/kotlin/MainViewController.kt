import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import presentation.navigation.DefaultRootComponent

@OptIn(ExperimentalDecomposeApi::class)
fun MainViewController() = ComposeUIViewController {
    val root = remember {
        DefaultRootComponent(DefaultComponentContext(LifecycleRegistry()))
    }
    App(root)
}
