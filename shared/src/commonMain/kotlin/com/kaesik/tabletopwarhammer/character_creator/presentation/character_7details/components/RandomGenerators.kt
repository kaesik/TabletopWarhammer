package com.kaesik.tabletopwarhammer.character_creator.presentation.character_7details.components

fun generateRandomName(species: String): NameParts {
    fun pick(vararg options: String) = options.random()

    return when (species.uppercase()) {
        "HUMAN" -> NameParts(
            name = pick(
                "Adhemar", "Anders", "Artur", "Beatrijs", "Clementia", "Detlev", "Erika", "Frauke",
                "Frederich", "Gerner", "Gertraud", "Haletha", "Heinrich", "Helga", "Henryk",
                "Irmina", "Jehanne", "Karl", "Kruger", "Lorelay", "Marieke", "Sebastien",
                "Sigfreda", "Talther", "Talunda", "Ulrich", "Ulrika", "Werther", "Willelma",
                "Wilryn"
            ),
            forename = "",
            surname = pick(
                "Bauer", "Fleischer", "Schmidt", "Schuster", "Lang", "Augenlos", "Dunn", "Laut",
                "Stark"
            ),
            clan = "",
            epithet = ""
        )

        "HALFLING" -> NameParts(
            name = pick(
                "Antoniella", "Esmerelda", "Ferdinand", "Heironymus", "Maximilian", "Theodosius",
                "Thomasina"
            ),
            forename = "",
            surname = "",
            clan = pick(
                "Ashfield", "Brandysnap", "Hayfoot", "Rumster", "Shortbottom", "Thorncobble"
            ),
            epithet = ""
        )

        "DWARF" -> NameParts(
            name = pick(
                "Alrik", "Bronda", "Dimzad", "Fenna", "Gottri", "Gudrun", "Snorri", "Baragaz",
                "Durak", "Galazil", "Gnoldok", "Nazril", "Okri"
            ),
            forename = pick(
                "-sdottir: Daughter of…", "-snev: Nephew of…", "-sniz: Niece of…", "-sson: Son of…"
            ),
            surname = "",
            clan = pick(
                "Ardrungan", "Bryntok", "Gazani", "Gromheld", "Harrazlings", "Unboki",
                "Dokkintroll", "Ganvalgger", "Kvitang", "Thrungtak", "Wyrgrinti", "Zankonk"
            ),
            epithet = ""
        )

        "HIGH ELF" -> NameParts(
            name = pick(
                "Emberfell", "Fireborn", "Foamheart", "Goldenhair", "Silverspray", "Spellsign"
            ),
            forename = generateElfForename(species),
            surname = "",
            clan = "",
            epithet = ""
        )

        "WOOD ELF" -> NameParts(
            name = pick(
                "Fleetriver", "Shadowstalker", "Treeshaper", "Weavewatcher", "Willowlimb",
                "Windrunner"
            ),
            forename = generateElfForename(species),
            surname = "",
            clan = "",
            epithet = ""
        )

        else -> NameParts("", "", "", "", "")
    }
}

data class NameParts(
    val name: String,
    val forename: String,
    val surname: String,
    val clan: String,
    val epithet: String
)

fun generateElfForename(species: String): String {
    val firstComponents =
        listOf("Aes", "Ath", "Dor", "Far", "Gal", "Im", "Lin", "Mal", "Mor", "Ullia")
    val secondComponents =
        listOf("a", "ath", "dia", "en", "for", "lor", "mar", "ol", "sor", "than")
    val highElfEndings =
        listOf("andril", "anel", "ellion", "fin", "il", "irian", "mor", "nil", "ric", "wing")
    val woodElfEndings =
        listOf("arha", "anhu", "dda", "han", "loc", "noc", "oth", "ryn", "stra", "wyth")

    val first = firstComponents.random()
    val second = secondComponents.random()
    val ending = when (species.uppercase()) {
        "HIGH ELF" -> highElfEndings.random()
        "WOOD ELF" -> woodElfEndings.random()
        else -> ""
    }

    return "$first$second$ending".replaceFirstChar { it.uppercaseChar() }
}

fun generateRandomAge(species: String): String {
    return when (species.uppercase()) {
        "HUMAN" -> (15 + (1..10).random()).toString()
        "HALFLING" -> (15 + List(5) { (1..10).random() }.sum()).toString()
        "DWARF" -> (15 + List(10) { (1..10).random() }.sum()).toString()
        "HIGH ELF" -> (30 + List(10) { (1..10).random() }.sum()).toString()
        "WOOD ELF" -> (30 + List(10) { (1..10).random() }.sum()).toString()
        else -> "?"
    }
}

fun generateRandomHeight(species: String): String {
    fun formatHeight(totalInches: Int): String {
        val feet = totalInches / 12
        val inches = totalInches % 12
        return "${feet}’${inches}”"
    }

    return when (species.uppercase()) {
        "HUMAN" -> formatHeight(57 + (1..10).random() + (1..10).random())
        "HALFLING" -> formatHeight(37 + (1..10).random())
        "DWARF" -> formatHeight(51 + (1..10).random())
        "HIGH ELF" -> formatHeight(71 + (1..10).random())
        "WOOD ELF" -> formatHeight(71 + (1..10).random())
        else -> "?"
    }
}

fun generateRandomHairColor(species: String): String {
    val roll = (1..10).random() + (1..10).random()

    return when (species.uppercase()) {
        "HUMAN" -> when (roll) {
            2 -> "White Blond"
            3 -> "Golden Blond"
            4 -> "Red Blond"
            in 5..7 -> "Golden Brown"
            in 8..11 -> "Light Brown"
            in 12..14 -> "Dark Brown"
            in 15..17 -> "Black"
            18 -> "Auburn"
            19 -> "Red"
            20 -> "Grey"
            else -> "Unknown"
        }

        "DWARF" -> when (roll) {
            2 -> "White"
            3 -> "Grey"
            4 -> "Pale Blond"
            in 5..7 -> "Golden"
            in 8..11 -> "Copper"
            in 12..14 -> "Bronze"
            in 15..17 -> "Brown"
            18 -> "Dark Brown"
            19 -> "Reddish Brown"
            20 -> "Black"
            else -> "Unknown"
        }

        "HALFLING" -> when (roll) {
            2 -> "Grey"
            3 -> "Flaxen"
            4 -> "Russet"
            in 5..7 -> "Honey Blond"
            in 8..11 -> "Chestnut"
            in 12..14 -> "Ginger"
            in 15..17 -> "Mustard"
            18 -> "Almond"
            19 -> "Chocolate"
            20 -> "Liquorice"
            else -> "Unknown"
        }

        "HIGH ELF" -> when (roll) {
            2 -> "Silver"
            3 -> "White"
            4 -> "Pale Blond"
            in 5..7 -> "Honey Blond"
            in 8..11 -> "Yellow Blond"
            in 12..14 -> "Copper Blond"
            in 15..17 -> "Red Blond"
            18 -> "Auburn"
            19 -> "Red"
            20 -> "Black"
            else -> "Unknown"
        }

        "WOOD ELF" -> when (roll) {
            2 -> "Birch Silver"
            3 -> "Ash Blond"
            4 -> "Rose Gold"
            in 5..7 -> "Honey Blond"
            in 8..11 -> "Brown"
            in 12..14 -> "Mahogany Brown"
            in 15..17 -> "Dark Brown"
            18 -> "Sienna"
            19 -> "Ebony"
            20 -> "Blue-Black"
            else -> "Unknown"
        }

        else -> "Unknown"
    }
}

fun generateRandomEyeColor(species: String): String {
    val roll = (1..10).random() + (1..10).random()

    return when (species.uppercase()) {
        "HUMAN" -> when (roll) {
            2 -> "Free Choice"
            3 -> "Green"
            4 -> "Pale Blue"
            in 5..7 -> "Blue"
            in 8..11 -> "Pale Grey"
            in 12..14 -> "Grey"
            in 15..17 -> "Brown"
            18 -> "Hazel"
            19 -> "Dark Brown"
            20 -> "Black"
            else -> "Unknown"
        }

        "DWARF" -> when (roll) {
            2 -> "Coal"
            3 -> "Lead"
            4 -> "Steel"
            in 5..7 -> "Blue"
            in 8..11 -> "Earth Brown"
            in 12..14 -> "Dark Brown"
            in 15..17 -> "Hazel"
            18 -> "Green"
            19 -> "Copper"
            20 -> "Gold"
            else -> "Unknown"
        }

        "HALFLING" -> when (roll) {
            2 -> "Light Grey"
            3 -> "Grey"
            4 -> "Pale Blue"
            in 5..7 -> "Blue"
            in 8..11 -> "Green"
            in 12..14 -> "Hazel"
            in 15..17 -> "Brown"
            18 -> "Copper"
            19, 20 -> "Dark Brown"
            else -> "Unknown"
        }

        "HIGH ELF" -> when (roll) {
            2 -> "Jet"
            3 -> "Amethyst"
            4 -> "Aquamarine"
            in 5..7 -> "Sapphire"
            in 8..11 -> "Turquoise"
            in 12..14 -> "Emerald"
            in 15..17 -> "Amber"
            18 -> "Copper"
            19 -> "Citrine"
            20 -> "Gold"
            else -> "Unknown"
        }

        "WOOD ELF" -> when (roll) {
            2 -> "Ivory"
            3 -> "Charcoal"
            4 -> "Ivy Green"
            in 5..7 -> "Mossy Green"
            in 8..11 -> "Chestnut"
            in 12..14 -> "Chestnut"
            in 15..17 -> "Dark Brown"
            18 -> "Tan"
            19 -> "Sandy Brown"
            20 -> "Violet"
            else -> "Unknown"
        }

        else -> "Unknown"
    }
}
