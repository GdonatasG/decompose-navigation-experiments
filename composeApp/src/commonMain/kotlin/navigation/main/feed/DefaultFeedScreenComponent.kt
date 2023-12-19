package navigation.main.feed

import com.arkivanov.decompose.ComponentContext

class DefaultFeedScreenComponent(
    componentContext: ComponentContext,
    private val delegate: Delegate
) : FeedScreenComponent, ComponentContext by componentContext {
    override fun onLogout() {
        delegate.onLogout()
    }

    override fun onDetails(id: Long) {
        delegate.onDetails(id = id)
    }

    interface Delegate {
        fun onLogout()
        fun onDetails(id: Long)
    }
}
