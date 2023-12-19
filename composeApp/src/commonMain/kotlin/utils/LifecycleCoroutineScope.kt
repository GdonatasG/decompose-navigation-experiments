package utils

import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.LifecycleOwner
import com.arkivanov.essenty.lifecycle.doOnDestroy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

fun CoroutineScope(scope: CoroutineScope, lifecycle: Lifecycle): CoroutineScope {
    lifecycle.doOnDestroy(scope::cancel)
    return scope
}

fun LifecycleOwner.lifecycleCoroutineScope(): CoroutineScope = CoroutineScope(MainScope(), lifecycle)
