package screen.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import customPredictiveBackAnimation
import navigation.main.MainComponent
import screen.main.feed_details.FeedDetailsScreen
import screen.main.tabs.MainBottomTabsScreen

@Composable
fun MainScreen(component: MainComponent) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Children(
            stack = component.childStack,
            modifier = Modifier.fillMaxSize(),
            animation = customPredictiveBackAnimation(
                backHandler = component.backHandler,
                onBack = component::onBackPressed
            )
        ) {
            when (val child = it.instance) {
                is MainComponent.Config.Tabs -> MainBottomTabsScreen(child.component)
                is MainComponent.Config.FeedDetails -> FeedDetailsScreen(child.component)
            }
        }
    }
}
