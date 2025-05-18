package com.kaesik.tabletopwarhammer.auth.presentation.intro

sealed class IntroEvent {
    data object OnSignInClick : IntroEvent()
    data object OnSignUpClick : IntroEvent()
    data object OnGuestClick : IntroEvent()
}
