package com.kaesik.tabletopwarhammer.core.presentation.di

import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController

val LocalNavController = compositionLocalOf<NavHostController> {
    error("LocalNavController not provided")
}
