import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.StackAnimation
import com.arkivanov.essenty.backhandler.BackHandler

expect fun <C : Any, T : Any> customPredictiveBackAnimation(
    backHandler: BackHandler,
    onBack: () -> Unit,
): StackAnimation<C, T>