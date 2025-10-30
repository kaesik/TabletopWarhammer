package com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_1creator.AndroidCharacterCreatorViewModel
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_1creator.CharacterCreatorEvent
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components.SpeciesOrCareer
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components.getBaseName
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components.skills.SkillsTable
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components.talents.TalentsTable
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.CharacterCreatorButton
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.CharacterCreatorSnackbarHost
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.SnackbarType
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.showCharacterCreatorSnackbar
import com.kaesik.tabletopwarhammer.core.domain.character.CharacterItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.SkillItem
import com.kaesik.tabletopwarhammer.core.presentation.MainScaffold
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.getKoin

@Composable
fun CharacterSkillsAndTalentsScreenRoot(
    viewModel: AndroidCharacterSkillsAndTalentsViewModel = koinViewModel(),
    creatorViewModel: AndroidCharacterCreatorViewModel = getKoin().get(),
    onNextClick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val creatorState by creatorViewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    // To avoid multiple restorations
    var restored by rememberSaveable { mutableStateOf(false) }

    // Current character
    val character = creatorViewModel.state.value.character

    // Handle messages from the creatorViewModel
    LaunchedEffect(creatorState.message, creatorState.isError) {
        creatorState.message?.let { message ->
            snackbarHostState.showCharacterCreatorSnackbar(
                message = message,
                type = if (creatorState.isError == true) SnackbarType.Error else SnackbarType.Success
            )
            creatorViewModel.onEvent(CharacterCreatorEvent.ClearMessage)
        }
    }

    // Initial data load
    LaunchedEffect(true) {
        // Load skills and talents for species
        viewModel.onEvent(
            CharacterSkillsAndTalentsEvent.InitSkillsList(
                from = SpeciesOrCareer.SPECIES,
                speciesName = character.species,
                careerPathName = character.careerPath
            )
        )
        viewModel.onEvent(
            CharacterSkillsAndTalentsEvent.InitTalentsList(
                from = SpeciesOrCareer.SPECIES,
                speciesName = character.species,
                careerPathName = character.careerPath
            )
        )
        // Load skills and talents for career
        viewModel.onEvent(
            CharacterSkillsAndTalentsEvent.InitSkillsList(
                from = SpeciesOrCareer.CAREER,
                speciesName = character.species,
                careerPathName = character.careerPath
            )
        )
        viewModel.onEvent(
            CharacterSkillsAndTalentsEvent.InitTalentsList(
                from = SpeciesOrCareer.CAREER,
                speciesName = character.species,
                careerPathName = character.careerPath
            )
        )
    }

    // Restore previous selections once skills and talents are loaded
    LaunchedEffect(
        state.speciesSkills, state.careerSkills,
        state.speciesTalentsList, state.careerTalentsList
    ) {
        // If already restored, skip
        if (restored) return@LaunchedEffect

        // Check if species and career data are loaded
        val speciesReady = state.speciesSkills.isNotEmpty() && state.speciesTalentsList.isNotEmpty()
        val careerReady = state.careerSkills.isNotEmpty() && state.careerTalentsList.isNotEmpty()
        val basicsReady = state.basicSkills.isNotEmpty()
        val allReady = speciesReady && careerReady && basicsReady
        if (!allReady) return@LaunchedEffect

        // Wait for both species and career data to be ready
        viewModel.onEvent(
            CharacterSkillsAndTalentsEvent.InitFromSelections(
                selectedSkillNames3 = creatorState.skillsTalentsSelectedSkillNames3,
                selectedSkillNames5 = creatorState.skillsTalentsSelectedSkillNames5,
                careerSkillPoints = creatorState.skillsTalentsCareerSkillPoints,
                selectedSpeciesTalentNames = creatorState.skillsTalentsSelectedSpeciesTalentNames,
                selectedCareerTalentNames = creatorState.skillsTalentsSelectedCareerTalentNames,
                rolledTalents = creatorState.skillsTalentsRolledTalents
            )
        )

        // Mark as restored to prevent future restorations
        restored = true
    }

    // Sync selections back to creatorViewModel when they change
    LaunchedEffect(
        restored,
        state.selectedSkills3, state.selectedSkills5, state.careerSkillPoints,
        state.selectedSpeciesTalents, state.selectedCareerTalents, state.rolledTalents
    ) {
        // Only sync after the initial restoration
        if (!restored) return@LaunchedEffect
        creatorViewModel.onEvent(
            CharacterCreatorEvent.SetSkillsTalentsSelections(
                selectedSkillNames3 = state.selectedSkills3.map { it.name },
                selectedSkillNames5 = state.selectedSkills5.map { it.name },
                careerSkillPoints = state.careerSkillPoints,
                selectedSpeciesTalentNames = state.selectedSpeciesTalents.map { it.name },
                selectedCareerTalentNames = state.selectedCareerTalents.map { it.name },
                rolledTalents = state.rolledTalents
            )
        )
    }

    // Decide which skills to show based on species or career
    val currentSkills =
        if (state.speciesOrCareer == SpeciesOrCareer.SPECIES) state.speciesSkills
        else state.careerSkills

    // Save skills and talents to the main creator state
    fun handleSaveSkillsAndTalents(
        state: CharacterSkillsAndTalentsState,
        character: CharacterItem
    ) {
        fun toSkillRow(skill: SkillItem, bonus: Int): List<String> {
            return listOf(
                skill.name,
                skill.attribute.orEmpty(),
                bonus.toString(),
                getAttributeValue(character, skill.attribute.orEmpty()).toString()
            )
        }

        fun specOf(name: String): String? {
            val i = name.indexOf('(')
            val j = name.lastIndexOf(')')
            return if (i in 0..<j) name.substring(i + 1, j).trim().ifBlank { null } else null
        }

        fun inferIsBasic(name: String, templateIsBasic: Boolean): Boolean {
            val base = getBaseName(name)
            val spec = specOf(name)?.lowercase()
            if (base.equals("Melee", true)) {
                return spec == "basic"
            }
            return if (spec != null) (templateIsBasic && spec == "basic") else templateIsBasic
        }

        fun normalizeMeleeBasic(skill: SkillItem): SkillItem {
            val base = getBaseName(skill.name)
            if (base.equals("Melee", true) && specOf(skill.name) == null) {
                return skill.copy(name = "Melee (Basic)", isBasic = true)
            }
            return skill
        }

        val speciesMap =
            (state.selectedSkills3.map { it to 3 } + state.selectedSkills5.map { it to 5 })
                .groupBy({ it.first.name }, { it })
                .mapValues { it.value.first() }

        val flatCareerSkills = state.careerSkills

        val careerMap =
            state.careerSkillPoints
                .filterValues { it > 0 }
                .mapNotNull { (name, bonus) ->
                    val exact = flatCareerSkills.find { it.name == name }
                    val templ = exact ?: run {
                        val baseSkill = flatCareerSkills.find {
                            getBaseName(it.name).equals(getBaseName(name), true)
                        }
                        baseSkill?.copy(
                            name = name,
                            isBasic = inferIsBasic(
                                name = name,
                                templateIsBasic = baseSkill.isBasic == true
                            )
                        )
                    }
                    templ?.let { it to bonus }
                }
                .groupBy({ it.first.name }, { it })
                .mapValues { it.value.first() }

        val allSkillsMap = (speciesMap.keys + careerMap.keys).associateWith { skillName ->
            val speciesEntry = speciesMap[skillName]
            val careerEntry = careerMap[skillName]

            val skill = speciesEntry?.first ?: careerEntry?.first!!
            val speciesBonus = speciesEntry?.second ?: 0
            val careerBonus = careerEntry?.second ?: 0
            val totalBonus = speciesBonus + careerBonus

            val normalizedSkill = if (specOf(skill.name) != null) {
                skill.copy(isBasic = inferIsBasic(skill.name, skill.isBasic == true))
            } else skill

            normalizeMeleeBasic(normalizedSkill) to totalBonus
        }

        val careerBasicSkills = mutableListOf<List<String>>()
        val careerAdvancedSkills = mutableListOf<List<String>>()

        allSkillsMap.values.forEach { (skill, totalBonus) ->
            val fixed = normalizeMeleeBasic(skill)
            if (fixed.isBasic == true)
                careerBasicSkills.add(toSkillRow(fixed, totalBonus))
            else
                careerAdvancedSkills.add(toSkillRow(fixed, totalBonus))
        }

        if (state.basicSkills.isNotEmpty()) {
            val presentNamesLC = allSkillsMap.keys.map { it.lowercase() }.toSet()
            state.basicSkills
                .filter { it.isBasic == true }
                .map { normalizeMeleeBasic(it) }
                .filter { it.name.lowercase() !in presentNamesLC }
                .forEach { missingBasic ->
                    careerBasicSkills.add(toSkillRow(missingBasic, 0))
                }
        }

        careerBasicSkills.sortBy { it[0].lowercase() }
        careerAdvancedSkills.sortBy { it[0].lowercase() }

        val allTalents = state.selectedSpeciesTalents + state.selectedCareerTalents
        val talents =
            allTalents.map { listOf(it.name, it.source.orEmpty(), it.page?.toString() ?: "") }

        creatorViewModel.onEvent(
            CharacterCreatorEvent.SetSkillsAndTalents(
                speciesBasicSkills = emptyList(),
                speciesAdvancedSkills = emptyList(),
                careerBasicSkills = careerBasicSkills,
                careerAdvancedSkills = careerAdvancedSkills,
                talents = talents
            )
        )
    }

    CharacterSkillsAndTalentsScreen(
        state = state,
        skills = currentSkills,
        speciesLabel = character.species,
        careerLabel = character.careerPath,
        snackbarHostState = snackbarHostState,
        onEvent = { event ->
            when (event) {
                // Enforce max 3 selections for +3 skills
                is CharacterSkillsAndTalentsEvent.OnSkillChecked3 -> {
                    if (event.isChecked && state.selectedSkills3.size >= 3) {
                        creatorViewModel.onEvent(
                            CharacterCreatorEvent.ShowMessage("You can only select 3 skills +3!")
                        )
                    } else {
                        viewModel.onEvent(event)
                    }
                }

                // Enforce max 3 selections for +5 skills
                is CharacterSkillsAndTalentsEvent.OnSkillChecked5 -> {
                    if (event.isChecked && state.selectedSkills5.size >= 3) {
                        creatorViewModel.onEvent(
                            CharacterCreatorEvent.ShowMessage("You can only select 3 skills +5!")
                        )
                    } else {
                        viewModel.onEvent(event)
                    }
                }

                // Handle switching between species and career modes
                is CharacterSkillsAndTalentsEvent.OnSaveSkillsAndTalents -> {
                    handleSaveSkillsAndTalents(state, character)
                }

                // Go to next module only if 40 points are allocated
                is CharacterSkillsAndTalentsEvent.OnNextClick -> {
                    if (state.totalAllocatedPoints != 40) {
                        creatorViewModel.onEvent(
                            CharacterCreatorEvent.ShowMessage("You must allocate exactly 40 points to career skills!")
                        )
                        return@CharacterSkillsAndTalentsScreen
                    }
                    handleSaveSkillsAndTalents(state, character)
                    onNextClick()
                }

                else -> viewModel.onEvent(event)
            }
        },
        requestTalentSpecializations = { baseName ->
            viewModel.onEvent(
                CharacterSkillsAndTalentsEvent.RequestTalentSpecializations(baseName)
            )
        },
        requestSkillSpecializations = { baseName ->
            viewModel.onEvent(
                CharacterSkillsAndTalentsEvent.RequestSkillSpecializations(baseName)
            )
        }
    )
}

// Helper to get attribute value from character based on attribute name
private fun getAttributeValue(character: CharacterItem, attributeName: String): Int {
    return when (attributeName) {
        "Weapon Skill" -> character.weaponSkill.firstOrNull() ?: 0
        "Ballistic Skill" -> character.ballisticSkill.firstOrNull() ?: 0
        "Strength" -> character.strength.firstOrNull() ?: 0
        "Toughness" -> character.toughness.firstOrNull() ?: 0
        "Initiative" -> character.initiative.firstOrNull() ?: 0
        "Agility" -> character.agility.firstOrNull() ?: 0
        "Dexterity" -> character.dexterity.firstOrNull() ?: 0
        "Intelligence" -> character.intelligence.firstOrNull() ?: 0
        "Willpower" -> character.willPower.firstOrNull() ?: 0
        "Fellowship" -> character.fellowship.firstOrNull() ?: 0
        else -> 0
    }
}

@Composable
fun CharacterSkillsAndTalentsScreen(
    state: CharacterSkillsAndTalentsState,
    skills: List<SkillItem>,
    speciesLabel: String,
    careerLabel: String,
    onEvent: (CharacterSkillsAndTalentsEvent) -> Unit,
    snackbarHostState: SnackbarHostState,
    requestTalentSpecializations: (String) -> Unit,
    requestSkillSpecializations: (String) -> Unit
) {
    MainScaffold(
        title = "Skills and Talents",
        snackbarHost = { CharacterCreatorSnackbarHost(snackbarHostState) },
        isLoading = state.isLoading,
        isError = state.isError,
        error = state.error,
        content = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // Table with skills
                item {
                    SkillsTable(
                        skills = skills,
                        selectedSkills3 = state.selectedSkills3,
                        selectedSkills5 = state.selectedSkills5,
                        speciesOrCareer = state.speciesOrCareer,
                        careerSkillPoints = state.careerSkillPoints,
                        onSkillChecked3 = { skill, isChecked ->
                            onEvent(
                                CharacterSkillsAndTalentsEvent.OnSkillChecked3(
                                    skill,
                                    isChecked
                                )
                            )
                        },
                        onSkillChecked5 = { skill, isChecked ->
                            onEvent(
                                CharacterSkillsAndTalentsEvent.OnSkillChecked5(
                                    skill,
                                    isChecked
                                )
                            )
                        },
                        onCareerPointsChanged = { skill, newValue ->
                            onEvent(
                                CharacterSkillsAndTalentsEvent.OnCareerSkillValueChanged(
                                    skill,
                                    newValue
                                )
                            )
                        },
                        skillOrGroups = state.skillOrGroups,
                        skillSpecializations = state.skillSpecializations,
                        loadingSkillSpecs = state.loadingSkillSpecs,
                        requestSkillSpecializations = requestSkillSpecializations
                    )
                }

                // Table with talents
                item {
                    TalentsTable(
                        talentsGroups = when (state.speciesOrCareer) {
                            SpeciesOrCareer.SPECIES -> state.speciesTalentsList
                            SpeciesOrCareer.CAREER -> state.careerTalentsList
                        },
                        selectedTalents = state.selectedTalents,
                        rolledTalents = state.rolledTalents,
                        speciesName = speciesLabel,
                        careerName = careerLabel,
                        isSpeciesMode = state.speciesOrCareer == SpeciesOrCareer.SPECIES,
                        onTalentChecked = { talent, isChecked ->
                            onEvent(
                                CharacterSkillsAndTalentsEvent.OnTalentChecked(
                                    talent,
                                    isChecked
                                )
                            )
                        },
                        onRandomTalentRolled = { groupIndex, talentIndex, rolledName ->
                            onEvent(
                                CharacterSkillsAndTalentsEvent.OnRandomTalentRolled(
                                    groupIndex, talentIndex, rolledName
                                )
                            )
                        },
                        talentSpecializations = state.talentSpecializations,
                        loadingTalentSpecs = state.loadingTalentSpecs,
                        requestTalentSpecializations = requestTalentSpecializations
                    )
                }

                // Button for switching modes and proceeding
                item {
                    if (state.speciesOrCareer == SpeciesOrCareer.CAREER) {
                        CharacterCreatorButton(
                            text = "Back to Species",
                            onClick = {
                                onEvent(CharacterSkillsAndTalentsEvent.OnSaveSkillsAndTalents)
                                onEvent(CharacterSkillsAndTalentsEvent.OnSpeciesOrCareerClick)
                            }
                        )
                    }
                }

                // Button for career skills and talents or next step
                item {
                    val buttonText = if (state.speciesOrCareer == SpeciesOrCareer.SPECIES) {
                        "Go to Career"
                    } else {
                        "Next"
                    }
                    CharacterCreatorButton(
                        text = buttonText,
                        onClick = {
                            onEvent(CharacterSkillsAndTalentsEvent.OnSaveSkillsAndTalents)
                            if (state.speciesOrCareer == SpeciesOrCareer.SPECIES) {
                                onEvent(CharacterSkillsAndTalentsEvent.OnSpeciesOrCareerClick)
                            } else {
                                onEvent(CharacterSkillsAndTalentsEvent.OnNextClick)
                            }
                        }
                    )
                }
            }
        }
    )
}

@Preview
@Composable
fun CharacterSkillsAndTalentsScreenPreview() {
    CharacterSkillsAndTalentsScreen(
        state = CharacterSkillsAndTalentsState(),
        skills = listOf(),
        speciesLabel = "Species",
        careerLabel = "Career",
        onEvent = { },
        snackbarHostState = remember { SnackbarHostState() },
        requestTalentSpecializations = { _ -> },
        requestSkillSpecializations = { _ -> }
    )
}
