package com.kaesik.tabletopwarhammer.library.presentation.library_3item

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kaesik.tabletopwarhammer.core.data.library.LibraryEnum
import com.kaesik.tabletopwarhammer.core.domain.library.items.AttributeItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.CareerItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.CareerPathItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.ClassItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.ItemItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.QualityFlawItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.SkillItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.SpeciesItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.TalentItem
import com.kaesik.tabletopwarhammer.library.presentation.library_3item.components.AttributeCard
import com.kaesik.tabletopwarhammer.library.presentation.library_3item.components.CareerCard
import com.kaesik.tabletopwarhammer.library.presentation.library_3item.components.CareerPathCard
import com.kaesik.tabletopwarhammer.library.presentation.library_3item.components.ClassCard
import com.kaesik.tabletopwarhammer.library.presentation.library_3item.components.ItemCard
import com.kaesik.tabletopwarhammer.library.presentation.library_3item.components.QualityFlawCard
import com.kaesik.tabletopwarhammer.library.presentation.library_3item.components.SkillCard
import com.kaesik.tabletopwarhammer.library.presentation.library_3item.components.SpeciesCard
import com.kaesik.tabletopwarhammer.library.presentation.library_3item.components.TalentCard
import org.koin.androidx.compose.koinViewModel

@Composable
fun LibraryItemScreenRoot(
    viewModel: AndroidLibraryItemViewModel = koinViewModel(),
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    itemId: String,
    fromTable: LibraryEnum
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(true) {
        viewModel.onEvent(
            LibraryItemEvent.InitItem(
                itemId = itemId,
                fromTable = fromTable
            )
        )
    }
    LibraryItemScreen(
        state = state,
        onEvent = { event ->
            when (event) {
                is LibraryItemEvent.OnBackClick -> onBackClick()
                is LibraryItemEvent.OnFavoriteClick -> onFavoriteClick()
                else -> Unit
            }

            viewModel.onEvent(event)
        }
    )
}

@Composable
fun LibraryItemScreen(
    state: LibraryItemState,
    onEvent: (LibraryItemEvent) -> Unit
) {
    Scaffold { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            item {
                Text("LibraryItemScreen")
            }
            item {
                state.libraryItem?.let { item ->
                    when (item) {
                        is AttributeItem -> {
                            AttributeCard(
                                name = item.name,
                                description = item.description,
                                shortName = item.shortName,
                                page = item.page
                            )
                        }

                        is CareerItem -> {
                            CareerCard(
                                name = item.name,
                                limitations = item.limitations,
                                description = item.description,
                                advanceScheme = item.advanceScheme,
                                quotations = item.quotations,
                                adventuring = item.adventuring,
                                source = item.source,
                                careerPath = item.careerPath,
                                className = item.className,
                                page = item.page
                            )
                        }

                        is CareerPathItem -> {
                            CareerPathCard(
                                name = item.name,
                                status = item.status,
                                skills = item.skills,
                                trappings = item.trappings,
                                talents = item.talents
                            )
                        }

                        is ClassItem -> {
                            ClassCard(
                                name = item.name,
                                description = item.description,
                                trappings = item.trappings,
                                careers = item.careers,
                                page = item.page,
                            )
                        }

                        is ItemItem -> {
                            ItemCard(
                                name = item.name,
                                group = item.group,
                                source = item.source,
                                ap = item.ap,
                                availability = item.availability,
                                carries = item.carries,
                                damage = item.damage,
                                description = item.description,
                                encumbrance = item.encumbrance,
                                isTwoHanded = item.isTwoHanded,
                                locations = item.locations,
                                penalty = item.penalty,
                                price = item.price,
                                qualitiesAndFlaws = item.qualitiesAndFlaws,
                                quantity = item.quantity,
                                range = item.range,
                                meeleRanged = item.meeleRanged,
                                type = item.type,
                                page = item.page
                            )
                        }

                        is QualityFlawItem -> {
                            QualityFlawCard(
                                name = item.name,
                                group = item.group,
                                description = item.description,
                                isQuality = item.isQuality,
                                source = item.source,
                                page = item.page
                            )
                        }

                        is SkillItem -> {
                            SkillCard(
                                name = item.name,
                                attribute = item.attribute,
                                isBasic = item.isBasic,
                                isGrouped = item.isGrouped,
                                description = item.description,
                                specialization = item.specialization,
                                source = item.source,
                                page = item.page
                            )
                        }

                        is SpeciesItem -> {
                            SpeciesCard(
                                name = item.name,
                                description = item.description,
                                opinions = item.opinions,
                                source = item.source,
                                weaponSkill = item.weaponSkill,
                                ballisticSkill = item.ballisticSkill,
                                strength = item.strength,
                                toughness = item.toughness,
                                agility = item.agility,
                                dexterity = item.dexterity,
                                intelligence = item.intelligence,
                                willpower = item.willpower,
                                fellowship = item.fellowship,
                                wounds = item.wounds,
                                fatePoints = item.fatePoints,
                                resilience = item.resilience,
                                extraPoints = item.extraPoints,
                                movement = item.movement,
                                skills = item.skills,
                                talents = item.talents,
                                forenames = item.forenames,
                                surnames = item.surnames,
                                clans = item.clans,
                                epithets = item.epithets,
                                age = item.age,
                                eyeColour = item.eyeColour,
                                hairColour = item.hairColour,
                                height = item.height,
                                initiative = item.initiative,
                                page = item.page,
                                names = item.names
                            )
                        }

                        is TalentItem -> {
                            TalentCard(
                                name = item.name,
                                max = item.max,
                                tests = item.tests,
                                description = item.description,
                                source = item.source,
                                page = item.page
                            )
                        }

                        else -> {
                            Text("Unknown item type")
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun LibraryItemScreenPreview() {
    LibraryItemScreen(
        state = LibraryItemState(),
        onEvent = {}
    )
}
