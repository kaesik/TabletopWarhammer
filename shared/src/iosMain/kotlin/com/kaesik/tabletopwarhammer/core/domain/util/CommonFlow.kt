package com.kaesik.tabletopwarhammer.core.domain.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual open class CommonFlow<T> actual constructor(
    private val flow: Flow<T>
) : Flow<T> by flow {

    fun subscribe(
        coroutineScope: CoroutineScope,
        dispatcher: CoroutineDispatcher,
        onCollect: (T) -> Unit,
    ): DisposableHandle {
        val job = coroutineScope.launch(dispatcher) {
            flow.collect(onCollect)
        }
        return DisposableHandle {
            job.cancel()
        }
    }
}
