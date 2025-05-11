package com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components

fun rollRandomTalent(excludedTalents: List<String> = emptyList()): String {
    val randomTalentsTable = mapOf(
        1..3 to "Acute Sense (any one)",
        4..6 to "Ambidextrous",
        7..9 to "Animal Affinity",
        10..12 to "Artistic",
        13..15 to "Attractive",
        16..18 to "Coolheaded",
        19..21 to "Craftsman (any one)",
        22..24 to "Flee!",
        25..28 to "Hardy",
        29..31 to "Lightning Reflexes",
        32..34 to "Linguistics",
        35..38 to "Luck",
        39..41 to "Marksman",
        42..44 to "Mimic",
        45..47 to "Night Vision",
        48..50 to "Nimble Fingered",
        51..52 to "Noble Blood",
        53..55 to "Orientation",
        56..58 to "Perfect Pitch",
        59..62 to "Pure Soul",
        63..65 to "Read/Write",
        66..68 to "Resistance (any one)",
        69..71 to "Savvy",
        72..74 to "Sharp",
        75..78 to "Sixth Sense",
        79..81 to "Strong Legs",
        82..84 to "Sturdy",
        85..87 to "Suave",
        88..91 to "Super Numerate",
        92..94 to "Very Resilient",
        95..97 to "Very Strong",
        98..100 to "Warrior Born"
    )

    val availableTalents = randomTalentsTable.values
        .filterNot { excludedTalents.contains(it) }

    if (availableTalents.isEmpty()) return "No Talents Left"

    return availableTalents.random()
}
