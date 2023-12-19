package screen.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import navigation.main.MainScreenComponent
import screen.main.feed_details.FeedDetailsScreen
import screen.main.tabs.MainBottomTabsScreen

@Composable
fun MainScreen(component: MainScreenComponent) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
        Children(
            stack = component.childStack,
            modifier = Modifier.fillMaxSize(),
            animation = stackAnimation(slide())
        ) {
            when (val child = it.instance) {
                is MainScreenComponent.Config.Tabs -> MainBottomTabsScreen(child.component)
                is MainScreenComponent.Config.FeedDetails -> FeedDetailsScreen(child.component)
            }
        }
    }
}
