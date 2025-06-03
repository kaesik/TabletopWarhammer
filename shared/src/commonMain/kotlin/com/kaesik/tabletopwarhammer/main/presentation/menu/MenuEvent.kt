package com.kaesik.tabletopwarhammer.main.presentation.menu

sealed class MenuEvent {
    data object NavigateToLibraryScreen : MenuEvent()
    data object NavigateToCharacterSheetScreen : MenuEvent()
    data object NavigateToCharacterCreatorScreen : MenuEvent()
}
