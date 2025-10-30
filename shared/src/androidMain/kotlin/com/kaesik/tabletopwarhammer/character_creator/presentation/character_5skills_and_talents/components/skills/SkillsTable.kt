package com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components.skills

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components.SeparatorOr
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components.SpeciesOrCareer
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components.getBaseName
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components.getFullNameWithSpecialization
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components.normalizeSkillOrTalentName
import com.kaesik.tabletopwarhammer.core.data.library.LibraryEnum
import com.kaesik.tabletopwarhammer.core.domain.info.InspectRef
import com.kaesik.tabletopwarhammer.core.domain.library.items.SkillItem
import com.kaesik.tabletopwarhammer.features.info.InspectInfoIcon
import com.kaesik.tabletopwarhammer.features.info.LocalOpenInfo

@Composable
fun SkillsTable(
    skills: List<SkillItem>,
    selectedSkills3: List<SkillItem>,
    selectedSkills5: List<SkillItem>,
    speciesOrCareer: SpeciesOrCareer,
    careerSkillPoints: Map<String, Int> = emptyMap(),
    onSkillChecked3: (SkillItem, Boolean) -> Unit,
    onSkillChecked5: (SkillItem, Boolean) -> Unit,
    onCareerPointsChanged: (SkillItem, Int) -> Unit = { _, _ -> },
    skillOrGroups: Map<String, Set<String>> = emptyMap(),
    skillSpecializations: Map<String, List<String>> = emptyMap(),
    loadingSkillSpecs: Set<String> = emptySet(),
    requestSkillSpecializations: (String) -> Unit = {}
) {
    val limitReached3 = selectedSkills3.size >= 3
    val limitReached5 = selectedSkills5.size >= 3

    val speciesAnySpec = remember { mutableStateMapOf<String, String>() }
    val careerAnySpec = remember { mutableStateMapOf<String, String>() }

    val canonSkills =
        remember(skills) { skills.map { it.copy(name = normalizeSkillOrTalentName(it.name)) } }
    val groups = remember(canonSkills) {
        canonSkills
            .groupBy { getBaseName(it.name) }
            .values
            .map { it.sortedBy { s -> s.name } }
            .toList()
    }

    fun shouldRenderAsOr(base: String, variants: List<SkillItem>): Boolean {
        val set = skillOrGroups[base] ?: return false
        return variants.isNotEmpty() && variants.all { normalizeSkillOrTalentName(it.name) in set }
    }

    fun hasAnyMarker(name: String): Boolean =
        Regex("\\bany\\b", RegexOption.IGNORE_CASE).containsMatchIn(name)

    fun extractSpec(fullName: String): String? {
        val inside = fullName.substringAfter("(", "").removeSuffix(")").trim()
        return inside.ifBlank { null }
    }

    fun nonAnyVariantNames(group: List<SkillItem>): Set<String> =
        group.filterNot { hasAnyMarker(it.name) }
            .map { normalizeSkillOrTalentName(it.name) }
            .toSet()

    fun nonAnyVariantSpecs(group: List<SkillItem>): Set<String> =
        group.filterNot { hasAnyMarker(it.name) }
            .mapNotNull { extractSpec(it.name)?.lowercase() }
            .toSet()

    fun findSelectedSpecForBaseInSpecies(base: String, groupNonAnyNames: Set<String>): String? {
        val chosen = (selectedSkills3 + selectedSkills5).firstOrNull {
            getBaseName(it.name).equals(base, true) &&
                    normalizeSkillOrTalentName(it.name) !in groupNonAnyNames
        } ?: return null
        return extractSpec(chosen.name)?.takeIf { !hasAnyMarker(it) }
    }

    fun findSelectedSpecForBaseInCareer(base: String, groupNonAnyNames: Set<String>): String? {
        val key = careerSkillPoints.keys.firstOrNull {
            getBaseName(it).equals(base, true) &&
                    normalizeSkillOrTalentName(it) !in groupNonAnyNames &&
                    !hasAnyMarker(it)
        } ?: return null
        return extractSpec(key)
    }

    @Composable
    fun SpecializationDialog(
        base: String,
        loading: Boolean,
        options: List<String>,
        forbiddenSpecsLower: Set<String>,
        onConfirm: (String) -> Unit,
        onDismiss: () -> Unit
    ) {
        var picked by remember(base, options, forbiddenSpecsLower) { mutableStateOf<String?>(null) }
        var custom by remember(base) { mutableStateOf("") }

        val filteredOptions = options.filterNot { it.lowercase() in forbiddenSpecsLower }
        val allowCustom = filteredOptions.any { it.equals("Other", true) } || options.any {
            it.equals(
                "Other",
                true
            )
        }

        val effectivePicked = when {
            picked == null -> null
            picked!!.equals("Other", true) && allowCustom -> custom.ifBlank { null }
            else -> picked
        }

        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(base) },
            text = {
                if (loading) {
                    Box(
                        Modifier
                            .height(120.dp)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) { CircularProgressIndicator() }
                } else {
                    Column {
                        val listToShow =
                            if (filteredOptions.isEmpty() && allowCustom) listOf("Other") else filteredOptions
                        if (listToShow.isEmpty()) {
                            Text("None")
                        } else {
                            Column(
                                Modifier
                                    .heightIn(max = 260.dp)
                                    .verticalScroll(rememberScrollState())
                            ) {
                                listToShow.forEach { opt ->
                                    Row(
                                        Modifier
                                            .fillMaxWidth()
                                            .clickable { picked = opt }
                                            .padding(vertical = 6.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        val mark = if (picked == opt) "  ✓" else ""
                                        Text(opt + mark)
                                    }
                                }
                            }
                        }
                        if (allowCustom && picked?.equals("Other", true) == true) {
                            OutlinedTextField(
                                value = custom,
                                onValueChange = { custom = it },
                                label = { Text("Other") },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(
                    enabled = !loading && effectivePicked != null,
                    onClick = { onConfirm(effectivePicked!!) }
                ) { Text("OK") }
            },
            dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } }
        )
    }

    Column {
        groups.forEach { group ->
            val base = getBaseName(group.first().name)
            val renderAsOr = group.size > 1 && shouldRenderAsOr(base, group)

            val groupNonAnyNames = nonAnyVariantNames(group)
            val groupNonAnySpecsLower = nonAnyVariantSpecs(group)

            when (speciesOrCareer) {
                SpeciesOrCareer.SPECIES -> {
                    group.forEach { skill ->
                        val isAny = hasAnyMarker(skill.name)

                        val isSelected3Base =
                            selectedSkills3.any { getBaseName(it.name).equals(base, true) }
                        val isSelected5Base =
                            selectedSkills5.any { getBaseName(it.name).equals(base, true) }

                        var showDialog by remember(skill.name) { mutableStateOf(false) }

                        if (!isAny) {
                            val isThis3 = selectedSkills3.any { it.name == skill.name }
                            val isThis5 = selectedSkills5.any { it.name == skill.name }
                            SkillTableItemSpecies(
                                skill = skill,
                                isSelected3 = isThis3,
                                isSelected5 = isThis5,
                                limitReached3 = limitReached3,
                                limitReached5 = limitReached5,
                                onCheckedChange3 = { checked ->
                                    if (checked && isThis5) onSkillChecked5(skill, false)
                                    onSkillChecked3(skill, checked)
                                },
                                onCheckedChange5 = { checked ->
                                    if (checked && isThis3) onSkillChecked3(skill, false)
                                    onSkillChecked5(skill, checked)
                                }
                            )
                        } else {
                            val chosenSpecPersisted = speciesAnySpec[base]
                            val chosenSpecFromSelection =
                                findSelectedSpecForBaseInSpecies(base, groupNonAnyNames)
                            val chosenSpec = chosenSpecPersisted ?: chosenSpecFromSelection
                            val displayName = if (chosenSpec != null)
                                getFullNameWithSpecialization(base, chosenSpec)
                            else
                                normalizeSkillOrTalentName("$base (Any)")

                            val openInfo = LocalOpenInfo.current

                            Surface(shadowElevation = 4.dp) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            showDialog = true
                                            requestSkillSpecializations(base)
                                        }
                                        .padding(horizontal = 8.dp, vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = displayName,
                                        modifier = Modifier.weight(2f)
                                    )

                                    InspectInfoIcon(
                                        onClick = {
                                            openInfo(
                                                InspectRef(
                                                    type = LibraryEnum.SKILL,
                                                    key = base
                                                )
                                            )
                                        }
                                    )

                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Checkbox(
                                            checked = isSelected3Base,
                                            onCheckedChange = { checked ->
                                                if (checked) {
                                                    val spec = chosenSpec ?: run {
                                                        showDialog = true
                                                        requestSkillSpecializations(base)
                                                        return@Checkbox
                                                    }
                                                    if (isSelected5Base) {
                                                        val selected5Item =
                                                            selectedSkills5.firstOrNull {
                                                                getBaseName(it.name).equals(
                                                                    base,
                                                                    true
                                                                )
                                                            }
                                                        selected5Item?.let {
                                                            onSkillChecked5(
                                                                it,
                                                                false
                                                            )
                                                        }
                                                    }
                                                    val resolved = skill.copy(
                                                        name = getFullNameWithSpecialization(
                                                            base,
                                                            spec
                                                        )
                                                    )
                                                    onSkillChecked3(resolved, true)
                                                } else {
                                                    val selected3Item =
                                                        selectedSkills3.firstOrNull {
                                                            getBaseName(it.name).equals(base, true)
                                                        }
                                                    selected3Item?.let {
                                                        onSkillChecked3(
                                                            it,
                                                            false
                                                        )
                                                    }
                                                }
                                            },
                                            enabled = isSelected3Base || !limitReached3
                                        )
                                        Text("+3")
                                        Checkbox(
                                            checked = isSelected5Base,
                                            onCheckedChange = { checked ->
                                                if (checked) {
                                                    val spec = chosenSpec ?: run {
                                                        showDialog = true
                                                        requestSkillSpecializations(base)
                                                        return@Checkbox
                                                    }
                                                    if (isSelected3Base) {
                                                        val selected3Item =
                                                            selectedSkills3.firstOrNull {
                                                                getBaseName(it.name).equals(
                                                                    base,
                                                                    true
                                                                )
                                                            }
                                                        selected3Item?.let {
                                                            onSkillChecked3(
                                                                it,
                                                                false
                                                            )
                                                        }
                                                    }
                                                    val resolved = skill.copy(
                                                        name = getFullNameWithSpecialization(
                                                            base,
                                                            spec
                                                        )
                                                    )
                                                    onSkillChecked5(resolved, true)
                                                } else {
                                                    val selected5Item =
                                                        selectedSkills5.firstOrNull {
                                                            getBaseName(it.name).equals(base, true)
                                                        }
                                                    selected5Item?.let {
                                                        onSkillChecked5(
                                                            it,
                                                            false
                                                        )
                                                    }
                                                }
                                            },
                                            enabled = isSelected5Base || !limitReached5
                                        )
                                        Text("+5")
                                    }
                                }
                            }

                            if (showDialog) {
                                SpecializationDialog(
                                    base = base,
                                    loading = loadingSkillSpecs.contains(base),
                                    options = skillSpecializations[base].orEmpty(),
                                    forbiddenSpecsLower = groupNonAnySpecsLower,
                                    onConfirm = { spec ->
                                        val specLower = spec.lowercase()
                                        if (specLower !in groupNonAnySpecsLower) {
                                            speciesAnySpec[base] = spec
                                        }
                                        showDialog = false
                                    },
                                    onDismiss = { showDialog = false }
                                )
                            }
                        }
                    }
                }

                SpeciesOrCareer.CAREER -> {
                    if (!renderAsOr) {
                        group.forEach { skill ->
                            val isAny = hasAnyMarker(skill.name)
                            var showDialog by remember(skill.name) { mutableStateOf(false) }

                            if (!isAny) {
                                val allocated = careerSkillPoints[skill.name] ?: 0
                                SkillTableItemCareer(
                                    skill = skill,
                                    allocatedPoints = allocated,
                                    onPointsChanged = { newValue ->
                                        onCareerPointsChanged(skill, newValue)
                                    }
                                )
                            } else {
                                val chosenSpecPersisted = careerAnySpec[base]
                                val chosenSpecFromMap =
                                    findSelectedSpecForBaseInCareer(base, groupNonAnyNames)
                                val chosenSpec = chosenSpecPersisted ?: chosenSpecFromMap
                                val resolvedName =
                                    chosenSpec?.let { getFullNameWithSpecialization(base, it) }
                                val displayName =
                                    resolvedName ?: normalizeSkillOrTalentName("${base} (Any)")

                                val keyForPoints = resolvedName ?: skill.name
                                val allocated = careerSkillPoints[keyForPoints] ?: 0

                                val openInfo = LocalOpenInfo.current

                                Surface(shadowElevation = 4.dp) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                showDialog = true
                                                requestSkillSpecializations(base)
                                            }
                                            .padding(horizontal = 8.dp, vertical = 4.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = displayName,
                                            modifier = Modifier.weight(2f),
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )

                                        InspectInfoIcon(
                                            onClick = {
                                                openInfo(
                                                    InspectRef(
                                                        type = LibraryEnum.SKILL,
                                                        key = base
                                                    )
                                                )
                                            }
                                        )

                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                                        ) {
                                            val enabled = resolvedName != null
                                            Slider(
                                                value = allocated.toFloat(),
                                                onValueChange = { v ->
                                                    if (enabled) {
                                                        val resolvedItem =
                                                            skill.copy(name = resolvedName!!)
                                                        onCareerPointsChanged(
                                                            resolvedItem,
                                                            v.toInt().coerceIn(0, 10)
                                                        )
                                                    }
                                                },
                                                valueRange = 0f..10f,
                                                steps = 9,
                                                modifier = Modifier.width(150.dp),
                                                enabled = enabled
                                            )
                                            Text("$allocated", modifier = Modifier.width(28.dp))
                                        }
                                    }
                                }

                                if (showDialog) {
                                    SpecializationDialog(
                                        base = base,
                                        loading = loadingSkillSpecs.contains(base),
                                        options = skillSpecializations[base].orEmpty(),
                                        forbiddenSpecsLower = groupNonAnySpecsLower,
                                        onConfirm = { spec ->
                                            val specLower = spec.lowercase()
                                            if (specLower in groupNonAnySpecsLower) {
                                                showDialog = false
                                                return@SpecializationDialog
                                            }
                                            val newResolved =
                                                getFullNameWithSpecialization(base, spec)
                                            val prevResolved = resolvedName
                                            val prevAllocated = when {
                                                prevResolved != null -> careerSkillPoints[prevResolved]
                                                    ?: 0

                                                else -> careerSkillPoints[skill.name] ?: 0
                                            }
                                            if (prevResolved != null) {
                                                onCareerPointsChanged(
                                                    skill.copy(name = prevResolved),
                                                    0
                                                )
                                            } else if (prevAllocated > 0) {
                                                onCareerPointsChanged(skill, 0)
                                            }
                                            onCareerPointsChanged(
                                                skill.copy(name = newResolved),
                                                prevAllocated
                                            )
                                            careerAnySpec[base] = spec
                                            showDialog = false
                                        },
                                        onDismiss = { showDialog = false }
                                    )
                                }
                            }
                        }
                    } else {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 2.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            group.forEachIndexed { idx, skill ->
                                val isAny = hasAnyMarker(skill.name)
                                var showDialog by remember(skill.name) { mutableStateOf(false) }

                                if (!isAny) {
                                    val allocated = careerSkillPoints[skill.name] ?: 0
                                    SkillTableItemCareer(
                                        skill = skill,
                                        allocatedPoints = allocated,
                                        onPointsChanged = { newValue ->
                                            if (newValue > 0) {
                                                group.forEach { other ->
                                                    if (other.name != skill.name &&
                                                        (careerSkillPoints[other.name] ?: 0) != 0
                                                    ) onCareerPointsChanged(other, 0)
                                                }
                                            }
                                            onCareerPointsChanged(skill, newValue)
                                        }
                                    )
                                } else {
                                    val chosenSpecPersisted = careerAnySpec[base]
                                    val chosenSpecFromMap =
                                        findSelectedSpecForBaseInCareer(base, groupNonAnyNames)
                                    val chosenSpec = chosenSpecPersisted ?: chosenSpecFromMap
                                    val resolvedName =
                                        chosenSpec?.let { getFullNameWithSpecialization(base, it) }
                                    val displayName =
                                        resolvedName ?: normalizeSkillOrTalentName("${base} (Any)")

                                    val keyForPoints = resolvedName ?: skill.name
                                    val allocated = careerSkillPoints[keyForPoints] ?: 0

                                    val openInfo = LocalOpenInfo.current

                                    Surface(shadowElevation = 4.dp) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clickable {
                                                    showDialog = true
                                                    requestSkillSpecializations(base)
                                                }
                                                .padding(horizontal = 8.dp, vertical = 4.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(
                                                text = displayName,
                                                modifier = Modifier.weight(2f),
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )

                                            InspectInfoIcon(
                                                onClick = {
                                                    openInfo(
                                                        InspectRef(
                                                            type = LibraryEnum.SKILL,
                                                            key = base
                                                        )
                                                    )
                                                }
                                            )

                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                                            ) {
                                                val enabled = resolvedName != null
                                                Slider(
                                                    value = allocated.toFloat(),
                                                    onValueChange = { v ->
                                                        if (enabled) {
                                                            val resolvedItem =
                                                                skill.copy(name = resolvedName!!)
                                                            if (v.toInt() > 0) {
                                                                group.forEach { other ->
                                                                    if (other.name != resolvedName &&
                                                                        (careerSkillPoints[other.name]
                                                                            ?: 0) != 0
                                                                    ) onCareerPointsChanged(
                                                                        other,
                                                                        0
                                                                    )
                                                                }
                                                            }
                                                            onCareerPointsChanged(
                                                                resolvedItem,
                                                                v.toInt().coerceIn(0, 10)
                                                            )
                                                        }
                                                    },
                                                    valueRange = 0f..10f,
                                                    steps = 9,
                                                    modifier = Modifier.width(150.dp),
                                                    enabled = enabled
                                                )
                                                Text("$allocated", modifier = Modifier.width(28.dp))
                                            }
                                        }
                                    }

                                    if (showDialog) {
                                        SpecializationDialog(
                                            base = base,
                                            loading = loadingSkillSpecs.contains(base),
                                            options = skillSpecializations[base].orEmpty(),
                                            forbiddenSpecsLower = groupNonAnySpecsLower,
                                            onConfirm = { spec ->
                                                val specLower = spec.lowercase()
                                                if (specLower in groupNonAnySpecsLower) {
                                                    showDialog = false
                                                    return@SpecializationDialog
                                                }
                                                val newResolved =
                                                    getFullNameWithSpecialization(base, spec)
                                                group.forEach { other ->
                                                    val pts = careerSkillPoints[other.name] ?: 0
                                                    if (pts != 0) onCareerPointsChanged(other, 0)
                                                }
                                                val prevResolved = resolvedName
                                                val prevAllocated = when {
                                                    prevResolved != null -> careerSkillPoints[prevResolved]
                                                        ?: 0

                                                    else -> careerSkillPoints[skill.name] ?: 0
                                                }
                                                if (prevResolved != null) {
                                                    onCareerPointsChanged(
                                                        skill.copy(name = prevResolved),
                                                        0
                                                    )
                                                } else if (prevAllocated > 0) {
                                                    onCareerPointsChanged(skill, 0)
                                                }
                                                onCareerPointsChanged(
                                                    skill.copy(name = newResolved),
                                                    prevAllocated
                                                )
                                                careerAnySpec[base] = spec
                                                showDialog = false
                                            },
                                            onDismiss = { showDialog = false }
                                        )
                                    }
                                }

                                if (idx < group.lastIndex) SeparatorOr()
                            }
                        }
                    }
                }
            }
        }
    }
}
