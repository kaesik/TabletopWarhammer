package com.kaesik.tabletopwarhammer.android.character_creator.presentation

import androidx.lifecycle.ViewModel
import com.kaesik.tabletopwarhammer.character_creator.presentation.CharacterCreatorEvent
import com.kaesik.tabletopwarhammer.character_creator.presentation.CharacterCreatorViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidCharacterCreatorViewModel  @Inject constructor(

): ViewModel() {

    private val viewModel by lazy {
        CharacterCreatorViewModel(

        )
    }

    val state = viewModel.state

    fun onEvent(event: CharacterCreatorEvent) {
        viewModel.onEvent(event)
    }
}
