package com.kaesik.tabletopwarhammer.android.character_creator.presentation

import androidx.lifecycle.ViewModel
import com.kaesik.tabletopwarhammer.character_creator.presentation.CharacterCreatorEvent
import com.kaesik.tabletopwarhammer.character_creator.presentation.CharacterCreatorViewModel

class AndroidCharacterCreatorViewModel(

) : ViewModel() {

    private val viewModel by lazy {
        CharacterCreatorViewModel(

        )
    }

    val state = viewModel.state

    fun onEvent(event: CharacterCreatorEvent) {
        viewModel.onEvent(event)
    }
}
