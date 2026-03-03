package com.kaesik.tabletopwarhammer.core.domain.util

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ExperimentalForInheritanceCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalForInheritanceCoroutinesApi::class)
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual open class CommonMutableStateFlow<T> actual constructor(
    private val flow: MutableStateFlow<T>
) : CommonStateFlow<T>(flow), MutableStateFlow<T> {

    actual override val replayCache: List<T>
        get() = flow.replayCache

    actual override val subscriptionCount: StateFlow<Int>
        get() = flow.subscriptionCount

    actual override var value: T
        get() = flow.value
        set(value) {
            flow.value = value
        }

    @ExperimentalCoroutinesApi
    actual override fun resetReplayCache() {
        flow.resetReplayCache()
    }

    actual override fun tryEmit(value: T): Boolean {
        return flow.tryEmit(value)
    }

    actual override suspend fun emit(value: T) {
        flow.emit(value)
    }

    actual override fun compareAndSet(expect: T, update: T): Boolean {
        return flow.compareAndSet(expect, update)
    }

    actual override suspend fun collect(collector: FlowCollector<T>): Nothing {
        flow.collect(collector)
    }
}
