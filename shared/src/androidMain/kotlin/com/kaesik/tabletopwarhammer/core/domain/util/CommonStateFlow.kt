package com.kaesik.tabletopwarhammer.core.domain.util

import kotlinx.coroutines.flow.StateFlow

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class CommonStateFlow<T> actual constructor(
    private val flow: StateFlow<T>
) : StateFlow<T> by flow
