package com.kaesik.tabletopwarhammer.menu.presentation

sealed class MenuEvent {
    data object NavigateToLibraryScreen : MenuEvent()
    data object NavigateToCharacterSheetScreen : MenuEvent()
    data object NavigateToCharacterCreatorScreen : MenuEvent()
    data object OnLogoutClick: MenuEvent()
}
