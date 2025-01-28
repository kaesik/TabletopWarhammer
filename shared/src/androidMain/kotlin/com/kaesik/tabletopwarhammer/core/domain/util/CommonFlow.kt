package com.kaesik.tabletopwarhammer.core.domain.util

import kotlinx.coroutines.flow.Flow

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class CommonFlow<T> actual constructor(
    private val flow: Flow<T>
) : Flow<T> by flow
