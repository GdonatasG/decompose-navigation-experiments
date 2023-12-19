package navigation.main.feed_details

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import utils.lifecycleCoroutineScope

class DefaultFeedDetailsScreenComponent(
    componentContext: ComponentContext, private val onBackCallback: () -> Unit
) : FeedDetailsScreenComponent, ComponentContext by componentContext {
    private val scope = lifecycleCoroutineScope()

    private val _count: MutableStateFlow<Int> = MutableStateFlow(0)
    override val count: StateFlow<Int> = _count.asStateFlow()

    init {
        scope.launch {
            while (isActive) {
                _count.value = _count.value + 1
                println("current count: ${_count.value}")
                delay(1000)
            }
        }
    }

    override fun onBack() {
        onBackCallback()
    }
}
