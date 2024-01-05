package presentation.navigation.main.feed_details

import com.arkivanov.decompose.ComponentContext
import domain.usecase.DummyUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import utils.lifecycleCoroutineScope

class DefaultFeedDetailsComponent(
    componentContext: ComponentContext, private val onBackCallback: () -> Unit
) : FeedDetailsComponent, ComponentContext by componentContext, KoinComponent {
    private val dummyUseCase: DummyUseCase by inject<DummyUseCase>()

    private val scope = lifecycleCoroutineScope()

    private val _count: MutableStateFlow<Int> = MutableStateFlow(0)
    override val count: StateFlow<Int> = _count.asStateFlow()

    init {
        scope.launch {
            println("Getting dummy data")
            val result = dummyUseCase.invoke()
            _count.value = result
            println("Dummy data fetched: $result")
        }
    }

    override fun onBack() {
        onBackCallback()
    }
}
