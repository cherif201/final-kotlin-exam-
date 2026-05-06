package com.example.heritagequest.data.questions

import com.example.heritagequest.domain.model.Category
import com.example.heritagequest.domain.model.Difficulty
import com.example.heritagequest.domain.model.Question

/**
 * Hardcoded question bank for the six heritage categories.
 *
 * Convention: the correct answer is always at index 0 in [Question.options].
 * The QuizViewModel shuffles options per session, so the UI never sees them
 * in this order. Keeping correctIndex = 0 here just makes the data easier to
 * read and review.
 *
 * wikipediaTitle is the Wikipedia article slug for the depicted site. The app
 * resolves the image URL at runtime via the Wikipedia REST API summary endpoint
 * (see WikipediaImageRepository) and loads it with Coil. No bundled photos.
 */
object QuestionBank {

    fun all(): List<Question> =
        roman + islamic + punic + modern + nature + cities

    fun forCategory(c: Category): List<Question> = when (c) {
        Category.ROMAN -> roman
        Category.ISLAMIC -> islamic
        Category.PUNIC -> punic
        Category.MODERN -> modern
        Category.NATURE -> nature
        Category.CITIES -> cities
    }

    fun forCategoryAndDifficulty(c: Category, d: Difficulty): List<Question> =
        forCategory(c).filter { it.difficulty == d }

    // ----------------------------------------------------------------------
    // Roman Heritage
    // ----------------------------------------------------------------------

    private val roman: List<Question> = listOf(

        // Easy ---------------------------------------------------------------
        Question(
            id = "rom_e_01",
            category = Category.ROMAN,
            difficulty = Difficulty.EASY,
            wikipediaTitle = "Amphitheatre_of_El_Jem",
            imageSource = "Wikimedia Commons",
            prompt = "What is the name of this monument?",
            options = listOf(
                "Amphitheater of El Jem",
                "Roman Theater of Carthage",
                "Theater of Dougga",
                "Forum of Sbeitla"
            ),
            correctIndex = 0,
            funFact = "Built around 238 AD, it is the largest Roman amphitheater in Africa and one of the best-preserved in the world."
        ),
        Question(
            id = "rom_e_02",
            category = Category.ROMAN,
            difficulty = Difficulty.EASY,
            wikipediaTitle = "Dougga",
            imageSource = "Wikimedia Commons",
            prompt = "Which Roman site is shown in this image?",
            options = listOf(
                "Capitol of Dougga",
                "Capitol of Sbeitla",
                "Capitol of Thuburbo Majus",
                "Capitol of Bulla Regia"
            ),
            correctIndex = 0,
            funFact = "Dougga is considered the best-preserved small Roman town in North Africa and has been a UNESCO site since 1997."
        ),
        Question(
            id = "rom_e_03",
            category = Category.ROMAN,
            difficulty = Difficulty.EASY,
            wikipediaTitle = "Baths_of_Antoninus",
            imageSource = "Wikimedia Commons",
            prompt = "What is the name of this Roman ruin?",
            options = listOf(
                "Antonine Baths",
                "Baths of Bulla Regia",
                "La Malga Cisterns",
                "Forum of Carthage"
            ),
            correctIndex = 0,
            funFact = "Located in Carthage and built in the 2nd century AD, these were among the largest bath complexes anywhere in the Roman Empire."
        ),
        Question(
            id = "rom_e_04",
            category = Category.ROMAN,
            difficulty = Difficulty.EASY,
            wikipediaTitle = "Sufetula",
            imageSource = "Wikimedia Commons",
            prompt = "What is the name of these three Roman temples?",
            options = listOf(
                "Capitoline Temples of Sbeitla",
                "Capitol of Dougga",
                "Forum of Maktar",
                "Temples of Thuburbo Majus"
            ),
            correctIndex = 0,
            funFact = "Sbeitla (ancient Sufetula) is one of the few sites in the Roman world with three separate temples to Jupiter, Juno, and Minerva instead of a single shared temple."
        ),
        Question(
            id = "rom_e_05",
            category = Category.ROMAN,
            difficulty = Difficulty.EASY,
            wikipediaTitle = "Theatre_of_Carthage",
            imageSource = "Wikimedia Commons",
            prompt = "What is this Roman monument?",
            options = listOf(
                "Roman Theater of Carthage",
                "Theater of Dougga",
                "Amphitheater of El Jem",
                "Theater of Sbeitla"
            ),
            correctIndex = 0,
            funFact = "Reconstructed during the 20th century, it now hosts the annual Carthage International Festival each summer."
        ),

        // Medium -------------------------------------------------------------
        Question(
            id = "rom_m_01",
            category = Category.ROMAN,
            difficulty = Difficulty.MEDIUM,
            wikipediaTitle = "Bulla_Regia",
            imageSource = "Wikimedia Commons",
            prompt = "What is this archaeological site known for its underground rooms?",
            options = listOf(
                "Bulla Regia",
                "Thuburbo Majus",
                "Uthina",
                "Maktar"
            ),
            correctIndex = 0,
            funFact = "Wealthy Romans here built entire underground floors to escape the summer heat, a solution found at this scale nowhere else in the Roman world."
        ),
        Question(
            id = "rom_m_02",
            category = Category.ROMAN,
            difficulty = Difficulty.MEDIUM,
            wikipediaTitle = "Thuburbo_Majus",
            imageSource = "Wikimedia Commons",
            prompt = "Which Roman city is shown here?",
            options = listOf(
                "Thuburbo Majus",
                "Maktar",
                "Bulla Regia",
                "Sbeitla"
            ),
            correctIndex = 0,
            funFact = "Originally a Punic settlement, it was Romanized in the 1st century BC and became prosperous through olive oil production."
        ),
        Question(
            id = "rom_m_03",
            category = Category.ROMAN,
            difficulty = Difficulty.MEDIUM,
            wikipediaTitle = "Mactaris",
            imageSource = "Wikimedia Commons",
            prompt = "What Roman site is shown in this image?",
            options = listOf(
                "Maktar",
                "Thuburbo Majus",
                "Bulla Regia",
                "Sbeitla"
            ),
            correctIndex = 0,
            funFact = "Originally a Numidian town, Maktar preserves the famous inscription of the 'Mactar Harvester' who rose from peasant to magistrate."
        ),
        Question(
            id = "rom_m_04",
            category = Category.ROMAN,
            difficulty = Difficulty.MEDIUM,
            wikipediaTitle = "Carthage",
            imageSource = "Wikimedia Commons",
            prompt = "What are these massive Roman water structures?",
            options = listOf(
                "La Malga Cisterns",
                "Antonine Baths",
                "Aghlabid Basins",
                "Roman Cisterns of El Jem"
            ),
            correctIndex = 0,
            funFact = "These cisterns supplied Carthage with water carried from springs near Zaghouan via one of the longest aqueducts of the Roman Empire."
        ),

        // Hard ---------------------------------------------------------------
        Question(
            id = "rom_h_01",
            category = Category.ROMAN,
            difficulty = Difficulty.HARD,
            wikipediaTitle = "Chemtou",
            imageSource = "Wikimedia Commons",
            prompt = "Which ancient Roman quarry site is shown here?",
            options = listOf(
                "Chemtou (Simitthus)",
                "Haïdra (Ammaedara)",
                "Althiburos",
                "Thala"
            ),
            correctIndex = 0,
            funFact = "Famous for its yellow giallo antico marble, prized across the Roman Empire and used in monuments from Rome to Constantinople."
        ),
        Question(
            id = "rom_h_02",
            category = Category.ROMAN,
            difficulty = Difficulty.HARD,
            wikipediaTitle = "Haïdra",
            imageSource = "Wikimedia Commons",
            prompt = "What is the name of this ancient Roman site?",
            options = listOf(
                "Haïdra (Ammaedara)",
                "Althiburos",
                "Chemtou",
                "Thuburbo Majus"
            ),
            correctIndex = 0,
            funFact = "Originally a base for the Roman Third Augustan Legion, it features one of the oldest Roman triumphal arches in Tunisia."
        ),
        Question(
            id = "rom_h_03",
            category = Category.ROMAN,
            difficulty = Difficulty.HARD,
            wikipediaTitle = "Uthina",
            imageSource = "Wikimedia Commons",
            prompt = "Which Roman colony is shown here?",
            options = listOf(
                "Uthina (Oudhna)",
                "Pheradi Maius",
                "Maktar",
                "Thuburbo Majus"
            ),
            correctIndex = 0,
            funFact = "One of the first Roman colonies in Africa, founded for veterans of Augustus, with a distinctive Capitol and amphitheater."
        ),
        Question(
            id = "rom_h_04",
            category = Category.ROMAN,
            difficulty = Difficulty.HARD,
            wikipediaTitle = "Althiburos",
            imageSource = "Wikimedia Commons",
            prompt = "Which Roman archaeological site is this?",
            options = listOf(
                "Althiburos",
                "Pheradi Maius",
                "Haïdra",
                "Chemtou"
            ),
            correctIndex = 0,
            funFact = "Inhabited from the Numidian period through late antiquity, it is famous for the mosaic of the 'Catalogue of Ships' now in the Bardo."
        )
    )

    // ----------------------------------------------------------------------
    // Islamic Heritage
    // ----------------------------------------------------------------------

    private val islamic: List<Question> = listOf(

        // Easy ---------------------------------------------------------------
        Question(
            id = "isl_e_01",
            category = Category.ISLAMIC,
            difficulty = Difficulty.EASY,
            wikipediaTitle = "Great_Mosque_of_Kairouan",
            imageSource = "Wikimedia Commons",
            prompt = "What is the name of this mosque?",
            options = listOf(
                "Great Mosque of Kairouan",
                "Zitouna Mosque",
                "Mosque of the Three Doors",
                "Sidi Mahrez Mosque"
            ),
            correctIndex = 0,
            funFact = "Founded in 670 AD, it is the oldest Muslim place of worship in the Maghreb and a model for North African mosque architecture."
        ),
        Question(
            id = "isl_e_02",
            category = Category.ISLAMIC,
            difficulty = Difficulty.EASY,
            wikipediaTitle = "Al-Zaytuna_Mosque",
            imageSource = "Wikimedia Commons",
            prompt = "Which mosque is shown in this image?",
            options = listOf(
                "Zitouna Mosque",
                "Great Mosque of Kairouan",
                "Sidi Mahrez Mosque",
                "Hammouda Pacha Mosque"
            ),
            correctIndex = 0,
            funFact = "Built in the 8th century in the heart of the Tunis medina, it housed one of the world's oldest universities for Islamic learning."
        ),
        Question(
            id = "isl_e_03",
            category = Category.ISLAMIC,
            difficulty = Difficulty.EASY,
            wikipediaTitle = "Ribat_of_Sousse",
            imageSource = "Wikimedia Commons",
            prompt = "What is the name of this fortified monastery?",
            options = listOf(
                "Ribat of Sousse",
                "Ribat of Monastir",
                "Kasbah of Hammamet",
                "Borj el Kebir of Mahdia"
            ),
            correctIndex = 0,
            funFact = "Built around 821 AD, it housed warriors who defended the coast and devoted themselves to religious study between attacks."
        ),
        Question(
            id = "isl_e_04",
            category = Category.ISLAMIC,
            difficulty = Difficulty.EASY,
            wikipediaTitle = "Ribat_of_Monastir",
            imageSource = "Wikimedia Commons",
            prompt = "Which ribat is shown here?",
            options = listOf(
                "Ribat of Monastir",
                "Ribat of Sousse",
                "Kasbah of Hammamet",
                "Borj el Kebir of Mahdia"
            ),
            correctIndex = 0,
            funFact = "Famously used as a filming location for Monty Python's Life of Brian and Zeffirelli's Jesus of Nazareth."
        ),
        Question(
            id = "isl_e_05",
            category = Category.ISLAMIC,
            difficulty = Difficulty.EASY,
            wikipediaTitle = "Medina_of_Tunis",
            imageSource = "Wikimedia Commons",
            prompt = "Which historic medina is this?",
            options = listOf(
                "Medina of Tunis",
                "Medina of Sousse",
                "Medina of Sfax",
                "Medina of Kairouan"
            ),
            correctIndex = 0,
            funFact = "A UNESCO World Heritage Site since 1979, it contains over 700 monuments including palaces, mosques, and madrasas."
        ),

        // Medium -------------------------------------------------------------
        Question(
            id = "isl_m_01",
            category = Category.ISLAMIC,
            difficulty = Difficulty.MEDIUM,
            wikipediaTitle = "Aghlabid_basins",
            imageSource = "Wikimedia Commons",
            prompt = "What are these large circular reservoirs?",
            options = listOf(
                "Aghlabid Basins of Kairouan",
                "Roman Cisterns of La Malga",
                "Sousse Reservoirs",
                "Mahdia Cisterns"
            ),
            correctIndex = 0,
            funFact = "Built in the 9th century, these basins held water carried from springs over 30 km away to supply medieval Kairouan."
        ),
        Question(
            id = "isl_m_02",
            category = Category.ISLAMIC,
            difficulty = Difficulty.MEDIUM,
            wikipediaTitle = "Mosque_of_the_Three_Doors",
            imageSource = "Wikimedia Commons",
            prompt = "Which mosque has this distinctive triple-arched facade?",
            options = listOf(
                "Mosque of the Three Doors",
                "Sidi Sahbi Mausoleum",
                "Zaouia of Sidi Bou Khrissan",
                "Mosque of the Barber"
            ),
            correctIndex = 0,
            funFact = "Built in 866 AD in Kairouan, it has the oldest decorated mosque facade in the Islamic world."
        ),
        Question(
            id = "isl_m_03",
            category = Category.ISLAMIC,
            difficulty = Difficulty.MEDIUM,
            wikipediaTitle = "Mosque_of_the_Barber",
            imageSource = "Wikimedia Commons",
            prompt = "Which religious complex is this?",
            options = listOf(
                "Sidi Sahbi Mausoleum",
                "Sidi Mahrez Mosque",
                "Zitouna Mosque",
                "Tourbet el Bey"
            ),
            correctIndex = 0,
            funFact = "Also called the Mosque of the Barber, it houses the tomb of a companion of the Prophet and features stunning ceramic tilework."
        ),
        Question(
            id = "isl_m_04",
            category = Category.ISLAMIC,
            difficulty = Difficulty.MEDIUM,
            wikipediaTitle = "Bardo_National_Museum",
            imageSource = "Wikimedia Commons",
            prompt = "Which palace, now a museum, is shown here?",
            options = listOf(
                "Bardo Palace",
                "Dar Hussein",
                "Dar Ben Abdallah",
                "Dar el Bey"
            ),
            correctIndex = 0,
            funFact = "Built as a Hafsid royal residence and later expanded by the Husseinid Beys, it now houses the world's largest collection of Roman mosaics."
        ),

        // Hard ---------------------------------------------------------------
        Question(
            id = "isl_h_01",
            category = Category.ISLAMIC,
            difficulty = Difficulty.HARD,
            wikipediaTitle = "Mahdia",
            imageSource = "Wikimedia Commons",
            prompt = "Which fortress is shown here?",
            options = listOf(
                "Borj el Kebir of Mahdia",
                "Kasbah of Hammamet",
                "Kasbah of Sousse",
                "Kasbah of Le Kef"
            ),
            correctIndex = 0,
            funFact = "Built by the Hafsids in the 16th century atop an earlier Fatimid fortress to defend against Spanish and Ottoman raids."
        ),
        Question(
            id = "isl_h_02",
            category = Category.ISLAMIC,
            difficulty = Difficulty.HARD,
            wikipediaTitle = "Tourbet_el_Bey",
            imageSource = "Wikimedia Commons",
            prompt = "What is the name of this royal mausoleum?",
            options = listOf(
                "Tourbet el Bey",
                "Sidi Bou Khrissan",
                "Tourbet Aziza Othmana",
                "Sidi Mahrez Mausoleum"
            ),
            correctIndex = 0,
            funFact = "Built in the 18th century in the Tunis medina, it is the official burial place of the Husseinid Beys who ruled Tunisia until 1957."
        ),
        Question(
            id = "isl_h_03",
            category = Category.ISLAMIC,
            difficulty = Difficulty.HARD,
            wikipediaTitle = "Sidi_Mahrez_Mosque",
            imageSource = "Wikimedia Commons",
            prompt = "Which Ottoman-era mosque, with multiple white domes, is this?",
            options = listOf(
                "Sidi Mahrez Mosque",
                "Hammouda Pacha Mosque",
                "Yousef Dey Mosque",
                "Sidi Yousef Mosque"
            ),
            correctIndex = 0,
            funFact = "Built in the 17th century in Ottoman Hanafi style, it is unique in Tunis for its cluster of white domes inspired by Istanbul mosques."
        ),
        Question(
            id = "isl_h_04",
            category = Category.ISLAMIC,
            difficulty = Difficulty.HARD,
            wikipediaTitle = "Hammouda_Pacha_Mosque",
            imageSource = "Wikimedia Commons",
            prompt = "Which mosque is shown in this image?",
            options = listOf(
                "Hammouda Pacha Mosque",
                "Yousef Dey Mosque",
                "Sidi Mahrez Mosque",
                "Sidi Yousef Mosque"
            ),
            correctIndex = 0,
            funFact = "Built in 1655 in the Tunis medina, it features an octagonal minaret unusual for the region and reflects strong Ottoman influence."
        )
    )

    // ----------------------------------------------------------------------
    // Punic & Pre-Roman
    // ----------------------------------------------------------------------

    private val punic: List<Question> = listOf(

        // Easy ---------------------------------------------------------------
        Question(
            id = "pun_e_01",
            category = Category.PUNIC,
            difficulty = Difficulty.EASY,
            wikipediaTitle = "Ports_of_Carthage",
            imageSource = "Wikimedia Commons",
            prompt = "What is this distinctive circular harbor?",
            options = listOf(
                "Punic Ports of Carthage (Cothon)",
                "Harbor of Kerkouane",
                "Harbor of Utica",
                "Harbor of Hadrumetum"
            ),
            correctIndex = 0,
            funFact = "The circular military port could shelter up to 220 warships, hidden from outside view by surrounding walls."
        ),
        Question(
            id = "pun_e_02",
            category = Category.PUNIC,
            difficulty = Difficulty.EASY,
            wikipediaTitle = "Byrsa",
            imageSource = "Wikimedia Commons",
            prompt = "Which hill, the historic heart of Carthage, is shown here?",
            options = listOf(
                "Byrsa Hill",
                "Bulla Regia acropolis",
                "Acropolis of Utica",
                "Hill of Kerkouane"
            ),
            correctIndex = 0,
            funFact = "Legend says Queen Dido founded Carthage by cutting an ox-hide into thin strips to claim as much land as it could enclose."
        ),
        Question(
            id = "pun_e_03",
            category = Category.PUNIC,
            difficulty = Difficulty.EASY,
            wikipediaTitle = "Kerkouane",
            imageSource = "Wikimedia Commons",
            prompt = "Which Punic site is shown here?",
            options = listOf(
                "Kerkouane",
                "Punic Quarter of Byrsa",
                "Utica",
                "Hadrumetum"
            ),
            correctIndex = 0,
            funFact = "The only well-preserved Punic city in the world, abandoned around 250 BC and never resettled. UNESCO World Heritage."
        ),
        Question(
            id = "pun_e_04",
            category = Category.PUNIC,
            difficulty = Difficulty.EASY,
            wikipediaTitle = "Tophet",
            imageSource = "Wikimedia Commons",
            prompt = "What ancient Punic sanctuary is this?",
            options = listOf(
                "Tophet of Carthage",
                "Punic Necropolis of Utica",
                "Tophet of Hadrumetum",
                "Punic Sanctuary of Kerkouane"
            ),
            correctIndex = 0,
            funFact = "An ancient Phoenician sanctuary debated by historians as either a child sacrifice site or a cemetery for infants who died young."
        ),

        // Medium -------------------------------------------------------------
        Question(
            id = "pun_m_01",
            category = Category.PUNIC,
            difficulty = Difficulty.MEDIUM,
            wikipediaTitle = "Byrsa",
            imageSource = "Wikimedia Commons",
            prompt = "Which Punic residential quarter, preserved under later Roman ruins, is this?",
            options = listOf(
                "Punic Quarter of Byrsa",
                "Punic Houses of Kerkouane",
                "Punic Quarter of Utica",
                "Punic Houses of Hadrumetum"
            ),
            correctIndex = 0,
            funFact = "These remains were buried under Roman construction and only rediscovered in the 20th century, preserving entire Punic-era streets."
        ),
        Question(
            id = "pun_m_02",
            category = Category.PUNIC,
            difficulty = Difficulty.MEDIUM,
            wikipediaTitle = "Utica,_Tunisia",
            imageSource = "Wikimedia Commons",
            prompt = "Which ancient port city, older than Carthage itself, is shown here?",
            options = listOf(
                "Utica",
                "Carthage",
                "Hadrumetum",
                "Hippo Diarrhytus"
            ),
            correctIndex = 0,
            funFact = "Founded around 1100 BC, it predates Carthage and is now located inland due to the silting of its ancient harbor."
        ),
        Question(
            id = "pun_m_03",
            category = Category.PUNIC,
            difficulty = Difficulty.MEDIUM,
            wikipediaTitle = "Tanit",
            imageSource = "Wikimedia Commons",
            prompt = "Which Punic religious symbol is shown carved on this stele?",
            options = listOf(
                "Symbol of Tanit",
                "Symbol of Baal Hammon",
                "Symbol of Eshmun",
                "Symbol of Melqart"
            ),
            correctIndex = 0,
            funFact = "The Tanit symbol, a triangle topped with a circle, is one of the most recognized icons of Phoenician-Punic religion."
        ),

        // Hard ---------------------------------------------------------------
        Question(
            id = "pun_h_01",
            category = Category.PUNIC,
            difficulty = Difficulty.HARD,
            wikipediaTitle = "Libyco-Punic_Mausoleum_of_Dougga",
            imageSource = "Wikimedia Commons",
            prompt = "Which pre-Roman tower-tomb is this?",
            options = listOf(
                "Libyo-Punic Mausoleum of Dougga",
                "Mausoleum of Sabratha",
                "Numidian Tomb of Maktar",
                "Mausoleum of Henchir Burgu"
            ),
            correctIndex = 0,
            funFact = "Built in the 2nd century BC, it is one of the few surviving examples of pre-Roman Numidian royal architecture and bears a bilingual Libyan-Punic inscription."
        ),
        Question(
            id = "pun_h_02",
            category = Category.PUNIC,
            difficulty = Difficulty.HARD,
            wikipediaTitle = "Carthage",
            imageSource = "Wikimedia Commons",
            prompt = "Which Punic burial site is this?",
            options = listOf(
                "Punic Necropolis of Carthage",
                "Necropolis of Kerkouane",
                "Necropolis of Utica",
                "Necropolis of Hadrumetum"
            ),
            correctIndex = 0,
            funFact = "These rock-cut tombs from the 4th–2nd centuries BC contained masks, jewelry, and amulets used in Punic burial rites."
        ),
        Question(
            id = "pun_h_03",
            category = Category.PUNIC,
            difficulty = Difficulty.HARD,
            wikipediaTitle = "Chemtou",
            imageSource = "Wikimedia Commons",
            prompt = "Which Numidian religious monument is shown here?",
            options = listOf(
                "Numidian Altar of Chemtou",
                "Sanctuary of Hoter Miskar at Maktar",
                "Sanctuary of Thinissut",
                "Punic Altar of Kerkouane"
            ),
            correctIndex = 0,
            funFact = "Predates the Roman conquest and reflects Numidian religious traditions before full Roman cultural assimilation."
        )
    )

    // ----------------------------------------------------------------------
    // Modern Heritage
    // ----------------------------------------------------------------------

    private val modern: List<Question> = listOf(

        // Easy ---------------------------------------------------------------
        Question(
            id = "mod_e_01",
            category = Category.MODERN,
            difficulty = Difficulty.EASY,
            wikipediaTitle = "Habib_Bourguiba_Mausoleum",
            imageSource = "Wikimedia Commons",
            prompt = "What is the name of this mausoleum?",
            options = listOf(
                "Habib Bourguiba Mausoleum",
                "Mausoleum of Sidi Sahbi",
                "Tourbet el Bey",
                "Mausoleum of Sidi Mahrez"
            ),
            correctIndex = 0,
            funFact = "Built in 1963 in Monastir for Tunisia's first president Habib Bourguiba, who is buried there alongside members of his family."
        ),
        Question(
            id = "mod_e_02",
            category = Category.MODERN,
            difficulty = Difficulty.EASY,
            wikipediaTitle = "Cathedral_of_St_Vincent_de_Paul,_Tunis",
            imageSource = "Wikimedia Commons",
            prompt = "Which cathedral is shown here?",
            options = listOf(
                "Cathedral of St. Vincent de Paul",
                "Saint Louis Cathedral of Carthage",
                "Sainte-Croix Church",
                "Notre-Dame de Tunis"
            ),
            correctIndex = 0,
            funFact = "Inaugurated in 1897 in Place de l'Indépendance, it remains the largest functioning Catholic cathedral in Tunisia."
        ),
        Question(
            id = "mod_e_03",
            category = Category.MODERN,
            difficulty = Difficulty.EASY,
            wikipediaTitle = "Bab_El_Bhar",
            imageSource = "Wikimedia Commons",
            prompt = "Which historic gate is this?",
            options = listOf(
                "Bab El Bhar (Porte de France)",
                "Bab Saadoun",
                "Bab Souika",
                "Bab Jedid"
            ),
            correctIndex = 0,
            funFact = "Originally one of the medina's main gates, it now stands isolated as the boundary between the old city and the French-built ville nouvelle."
        ),
        Question(
            id = "mod_e_04",
            category = Category.MODERN,
            difficulty = Difficulty.EASY,
            wikipediaTitle = "Municipal_Theatre_of_Tunis",
            imageSource = "Wikimedia Commons",
            prompt = "Which Art Nouveau building on Avenue Habib Bourguiba is this?",
            options = listOf(
                "Théâtre Municipal de Tunis",
                "Hotel Majestic",
                "Casino du Belvédère",
                "Cinéma Le Colisée"
            ),
            correctIndex = 0,
            funFact = "Built in 1902 in pure Art Nouveau style, it has hosted opera, theater, and ballet performances for over a century."
        ),
        Question(
            id = "mod_e_05",
            category = Category.MODERN,
            difficulty = Difficulty.EASY,
            wikipediaTitle = "Saint_Louis_Cathedral_of_Carthage",
            imageSource = "Wikimedia Commons",
            prompt = "Which former cathedral, now a cultural center, is this?",
            options = listOf(
                "Saint Louis Cathedral (Acropolium)",
                "Cathedral of St. Vincent de Paul",
                "Sainte-Croix Church",
                "Cathedral of Bizerte"
            ),
            correctIndex = 0,
            funFact = "Built in 1890 atop Byrsa Hill in Carthage, it was deconsecrated after independence and now serves as a concert and exhibition venue."
        ),

        // Medium -------------------------------------------------------------
        Question(
            id = "mod_m_01",
            category = Category.MODERN,
            difficulty = Difficulty.MEDIUM,
            wikipediaTitle = "Avenue_Habib_Bourguiba",
            imageSource = "Wikimedia Commons",
            prompt = "Which famous avenue, often called the Champs-Élysées of Tunis, is this?",
            options = listOf(
                "Avenue Habib Bourguiba",
                "Avenue de France",
                "Avenue Mohammed V",
                "Avenue de Carthage"
            ),
            correctIndex = 0,
            funFact = "Lined with ficus trees and ending at a monumental clock tower built in 2001, it has been the political heart of modern Tunis."
        ),
        Question(
            id = "mod_m_02",
            category = Category.MODERN,
            difficulty = Difficulty.MEDIUM,
            wikipediaTitle = "Avenue_Habib_Bourguiba",
            imageSource = "Wikimedia Commons",
            prompt = "Which monument stands at this end of Avenue Habib Bourguiba?",
            options = listOf(
                "Place du 14 Janvier Clock Tower",
                "Bab El Bhar",
                "Place Barcelone Monument",
                "Avenue Mohammed V Obelisk"
            ),
            correctIndex = 0,
            funFact = "Built in 1989 to commemorate a political transition, the surrounding square was renamed in 2011 after the date that ended Ben Ali's rule."
        ),
        Question(
            id = "mod_m_03",
            category = Category.MODERN,
            difficulty = Difficulty.MEDIUM,
            wikipediaTitle = "Belvedere_Park,_Tunis",
            imageSource = "Wikimedia Commons",
            prompt = "Which 19th-century park pavilion in Tunis is shown here?",
            options = listOf(
                "Koubba of Belvédère Park",
                "Pavillon de la Marsa",
                "Kiosque de Bab Saadoun",
                "Pavillon de Carthage"
            ),
            correctIndex = 0,
            funFact = "Originally a Hafsid-era palace pavilion that was relocated stone-by-stone to Belvédère Park, the largest urban park in Tunis."
        ),

        // Hard ---------------------------------------------------------------
        Question(
            id = "mod_h_01",
            category = Category.MODERN,
            difficulty = Difficulty.HARD,
            wikipediaTitle = "City_of_Culture,_Tunis",
            imageSource = "Wikimedia Commons",
            prompt = "Which contemporary cultural complex on Avenue Mohammed V is this?",
            options = listOf(
                "Cité de la Culture",
                "National Library",
                "Bardo Museum extension",
                "Tunis Opera"
            ),
            correctIndex = 0,
            funFact = "Inaugurated in 2018, it hosts theaters, an opera hall, and exhibition spaces under one roof."
        ),
        Question(
            id = "mod_h_02",
            category = Category.MODERN,
            difficulty = Difficulty.HARD,
            wikipediaTitle = "Bardo_National_Museum",
            imageSource = "Wikimedia Commons",
            prompt = "Which museum extension, completed in 2012, is shown here?",
            options = listOf(
                "Bardo National Museum modern wing",
                "National Library of Tunisia",
                "Cité des Sciences",
                "Museum of Modern Art of Tunis"
            ),
            correctIndex = 0,
            funFact = "Almost doubled the museum's exhibition space and lets visitors view mosaics in natural light through a glass-roofed atrium."
        ),
        Question(
            id = "mod_h_03",
            category = Category.MODERN,
            difficulty = Difficulty.HARD,
            wikipediaTitle = "El_Mouradi_Africa_Hotel",
            imageSource = "Wikimedia Commons",
            prompt = "Which 1970s tower, the tallest building of post-independence Tunis, is this?",
            options = listOf(
                "Hotel Africa Tunis",
                "Hotel El Mechtel",
                "Hotel Carlton",
                "Hotel Majestic"
            ),
            correctIndex = 0,
            funFact = "Inaugurated in 1970 on Avenue Habib Bourguiba, it was for decades the tallest building in Tunisia and a symbol of post-independence modernism."
        )
    )

    // ----------------------------------------------------------------------
    // Natural & Mixed Sites
    // ----------------------------------------------------------------------

    private val nature: List<Question> = listOf(

        // Easy ---------------------------------------------------------------
        Question(
            id = "nat_e_01",
            category = Category.NATURE,
            difficulty = Difficulty.EASY,
            wikipediaTitle = "Sidi_Bou_Said",
            imageSource = "Wikimedia Commons",
            prompt = "Which iconic blue-and-white Tunisian village is this?",
            options = listOf(
                "Sidi Bou Saïd",
                "Old Hammamet",
                "Old Mahdia",
                "La Marsa"
            ),
            correctIndex = 0,
            funFact = "The famous blue-and-white color scheme was made mandatory by Baron Rodolphe d'Erlanger in the early 20th century to preserve aesthetic harmony."
        ),
        Question(
            id = "nat_e_02",
            category = Category.NATURE,
            difficulty = Difficulty.EASY,
            wikipediaTitle = "Chott_el_Djerid",
            imageSource = "Wikimedia Commons",
            prompt = "What is the name of this vast salt flat?",
            options = listOf(
                "Chott el Djerid",
                "Chott el Gharsa",
                "Chott el Fejaj",
                "Sebkha el Melah"
            ),
            correctIndex = 0,
            funFact = "Tunisia's largest salt lake, covering thousands of square kilometers. The Lars homestead from Star Wars was filmed at its edge."
        ),
        Question(
            id = "nat_e_03",
            category = Category.NATURE,
            difficulty = Difficulty.EASY,
            wikipediaTitle = "Matmata,_Tunisia",
            imageSource = "Wikimedia Commons",
            prompt = "Which Berber troglodyte village is shown here?",
            options = listOf(
                "Matmata",
                "Chenini",
                "Douiret",
                "Toujane"
            ),
            correctIndex = 0,
            funFact = "These underground Berber homes inspired Luke Skywalker's house in Star Wars; the Hotel Sidi Driss is still a working hotel in one of them."
        ),
        Question(
            id = "nat_e_04",
            category = Category.NATURE,
            difficulty = Difficulty.EASY,
            wikipediaTitle = "Tozeur",
            imageSource = "Wikimedia Commons",
            prompt = "Which Tunisian oasis is shown here?",
            options = listOf(
                "Palm grove of Tozeur",
                "Oasis of Douz",
                "Oasis of Kebili",
                "Oasis of Nefta"
            ),
            correctIndex = 0,
            funFact = "Contains hundreds of thousands of palm trees, irrigated by a system designed by mathematician Ibn Chabbat in the 13th century."
        ),
        Question(
            id = "nat_e_05",
            category = Category.NATURE,
            difficulty = Difficulty.EASY,
            wikipediaTitle = "Ichkeul_National_Park",
            imageSource = "Wikimedia Commons",
            prompt = "Which national park, a UNESCO biosphere, is shown here?",
            options = listOf(
                "Ichkeul National Park",
                "Bou Hedma National Park",
                "Djebel Chambi National Park",
                "El Feidja National Park"
            ),
            correctIndex = 0,
            funFact = "The last remaining freshwater lake in a chain that once stretched across North Africa, hosting hundreds of thousands of migratory birds."
        ),

        // Medium -------------------------------------------------------------
        Question(
            id = "nat_m_01",
            category = Category.NATURE,
            difficulty = Difficulty.MEDIUM,
            wikipediaTitle = "Chenini",
            imageSource = "Wikimedia Commons",
            prompt = "Which mountain Berber village is this?",
            options = listOf(
                "Chenini",
                "Douiret",
                "Toujane",
                "Matmata"
            ),
            correctIndex = 0,
            funFact = "A fortified Berber village clinging to a mountainside, with an underground mosque said to be over a thousand years old."
        ),
        Question(
            id = "nat_m_02",
            category = Category.NATURE,
            difficulty = Difficulty.MEDIUM,
            wikipediaTitle = "Dahar",
            imageSource = "Wikimedia Commons",
            prompt = "Which mountainous southeastern Tunisian region is shown here?",
            options = listOf(
                "Dahar plateau",
                "Cap Bon plains",
                "Sahel coast",
                "Cap Negro hills"
            ),
            correctIndex = 0,
            funFact = "A semi-arid plateau dotted with ksour and Berber villages, used as a filming location for several Star Wars planets."
        ),
        Question(
            id = "nat_m_03",
            category = Category.NATURE,
            difficulty = Difficulty.MEDIUM,
            wikipediaTitle = "Zaghouan",
            imageSource = "Wikimedia Commons",
            prompt = "Which mountain, source of the Roman aqueduct of Carthage, is this?",
            options = listOf(
                "Djebel Zaghouan",
                "Djebel Bargou",
                "Djebel Ressas",
                "Djebel Boukornine"
            ),
            correctIndex = 0,
            funFact = "Its springs supplied Carthage with water through a long aqueduct still partially visible across the countryside today."
        ),

        // Hard ---------------------------------------------------------------
        Question(
            id = "nat_h_01",
            category = Category.NATURE,
            difficulty = Difficulty.HARD,
            wikipediaTitle = "Ksar_Ouled_Soltane",
            imageSource = "Wikimedia Commons",
            prompt = "Which fortified granary is shown here?",
            options = listOf(
                "Ksar Ouled Soltane",
                "Ksar Hadada",
                "Ksar Ouled Debbab",
                "Ksar Ghilane"
            ),
            correctIndex = 0,
            funFact = "A traditional Berber granary with multi-story ghorfas for storing grain, used as a Star Wars Episode I filming location."
        ),
        Question(
            id = "nat_h_02",
            category = Category.NATURE,
            difficulty = Difficulty.HARD,
            wikipediaTitle = "Jebel_ech_Chambi",
            imageSource = "Wikimedia Commons",
            prompt = "Which mountain, Tunisia's highest peak, is shown here?",
            options = listOf(
                "Djebel Chambi",
                "Djebel Bargou",
                "Djebel Zaghouan",
                "Djebel Mghila"
            ),
            correctIndex = 0,
            funFact = "At about 1,544 m it is Tunisia's highest peak, located in a national park near the Algerian border."
        ),
        Question(
            id = "nat_h_03",
            category = Category.NATURE,
            difficulty = Difficulty.HARD,
            wikipediaTitle = "Bou-Hedma_National_Park",
            imageSource = "Wikimedia Commons",
            prompt = "Which national park is this?",
            options = listOf(
                "Bou Hedma National Park",
                "Ichkeul National Park",
                "Djebel Chambi National Park",
                "El Feidja National Park"
            ),
            correctIndex = 0,
            funFact = "Home to a rare Saharan acacia forest and to reintroduced populations of scimitar oryx and addax antelopes."
        ),
        Question(
            id = "nat_h_04",
            category = Category.NATURE,
            difficulty = Difficulty.HARD,
            wikipediaTitle = "Cap_Bon",
            imageSource = "Wikimedia Commons",
            prompt = "Which northeastern peninsula is shown here?",
            options = listOf(
                "Cap Bon",
                "Cap Serrat",
                "Cap Negro",
                "Cap Blanc"
            ),
            correctIndex = 0,
            funFact = "Tunisia's fertile northeastern peninsula, known for citrus groves, vineyards, and a long coastline of beaches and cliffs."
        )
    )

    // ----------------------------------------------------------------------
    // Cities
    // ----------------------------------------------------------------------

    private val cities: List<Question> = listOf(

        // Easy ---------------------------------------------------------------
        Question(
            id = "cit_e_01",
            category = Category.CITIES,
            difficulty = Difficulty.EASY,
            wikipediaTitle = "Tunis",
            imageSource = "Wikimedia Commons",
            prompt = "Which Tunisian city is shown here?",
            options = listOf(
                "Tunis",
                "Sousse",
                "Sfax",
                "Kairouan"
            ),
            correctIndex = 0,
            funFact = "The capital, founded over three thousand years ago, with a UNESCO-listed medina built around the Zitouna Mosque."
        ),
        Question(
            id = "cit_e_02",
            category = Category.CITIES,
            difficulty = Difficulty.EASY,
            wikipediaTitle = "Sousse",
            imageSource = "Wikimedia Commons",
            prompt = "Which coastal city is this?",
            options = listOf(
                "Sousse",
                "Mahdia",
                "Monastir",
                "Hammamet"
            ),
            correctIndex = 0,
            funFact = "Known as the Pearl of the Sahel, its medina is a UNESCO site combining Aghlabid fortifications with Mediterranean coastal life."
        ),
        Question(
            id = "cit_e_03",
            category = Category.CITIES,
            difficulty = Difficulty.EASY,
            wikipediaTitle = "Kairouan",
            imageSource = "Wikimedia Commons",
            prompt = "Which holy city is this?",
            options = listOf(
                "Kairouan",
                "Mahdia",
                "Sousse",
                "Sfax"
            ),
            correctIndex = 0,
            funFact = "Founded in 670 AD, it is widely considered the fourth holiest city in Islam after Mecca, Medina, and Jerusalem."
        ),
        Question(
            id = "cit_e_04",
            category = Category.CITIES,
            difficulty = Difficulty.EASY,
            wikipediaTitle = "Sfax",
            imageSource = "Wikimedia Commons",
            prompt = "Which Tunisian city is shown here?",
            options = listOf(
                "Sfax",
                "Mahdia",
                "Sousse",
                "Bizerte"
            ),
            correctIndex = 0,
            funFact = "Tunisia's second-largest city and economic capital, with one of the most complete fortified medinas in North Africa."
        ),
        Question(
            id = "cit_e_05",
            category = Category.CITIES,
            difficulty = Difficulty.EASY,
            wikipediaTitle = "Hammamet",
            imageSource = "Wikimedia Commons",
            prompt = "Which seaside resort city is this?",
            options = listOf(
                "Hammamet",
                "Sousse",
                "Mahdia",
                "Nabeul"
            ),
            correctIndex = 0,
            funFact = "A coastal resort whose 15th-century kasbah has guarded the medina for over five hundred years."
        ),

        // Medium -------------------------------------------------------------
        Question(
            id = "cit_m_01",
            category = Category.CITIES,
            difficulty = Difficulty.MEDIUM,
            wikipediaTitle = "Bizerte",
            imageSource = "Wikimedia Commons",
            prompt = "Which northern port city is shown here?",
            options = listOf(
                "Bizerte",
                "Tabarka",
                "La Goulette",
                "Mahdia"
            ),
            correctIndex = 0,
            funFact = "Tunisia's northernmost city, with a picturesque old port that has been used since Phoenician times."
        ),
        Question(
            id = "cit_m_02",
            category = Category.CITIES,
            difficulty = Difficulty.MEDIUM,
            wikipediaTitle = "Mahdia",
            imageSource = "Wikimedia Commons",
            prompt = "Which historic peninsular city is this?",
            options = listOf(
                "Mahdia",
                "Monastir",
                "Sousse",
                "Hammamet"
            ),
            correctIndex = 0,
            funFact = "Founded in 916 AD as the first capital of the Fatimid Caliphate, on a narrow peninsula jutting into the Mediterranean."
        ),
        Question(
            id = "cit_m_03",
            category = Category.CITIES,
            difficulty = Difficulty.MEDIUM,
            wikipediaTitle = "Tabarka",
            imageSource = "Wikimedia Commons",
            prompt = "Which northwestern coastal town with a Genoese island fort is this?",
            options = listOf(
                "Tabarka",
                "Bizerte",
                "Cap Negro",
                "Cap Serrat"
            ),
            correctIndex = 0,
            funFact = "Known for coral fishing and a 16th-century Genoese fortress on a small island just off the coast."
        ),
        Question(
            id = "cit_m_04",
            category = Category.CITIES,
            difficulty = Difficulty.MEDIUM,
            wikipediaTitle = "Monastir",
            imageSource = "Wikimedia Commons",
            prompt = "Which coastal city, birthplace of Habib Bourguiba, is this?",
            options = listOf(
                "Monastir",
                "Mahdia",
                "Sousse",
                "Hammamet"
            ),
            correctIndex = 0,
            funFact = "Birthplace of Tunisia's first president and home to one of the oldest Islamic ribats in North Africa."
        ),

        // Hard ---------------------------------------------------------------
        Question(
            id = "cit_h_01",
            category = Category.CITIES,
            difficulty = Difficulty.HARD,
            wikipediaTitle = "Tozeur",
            imageSource = "Wikimedia Commons",
            prompt = "Which oasis city, with distinctive yellow brick architecture, is this?",
            options = listOf(
                "Tozeur",
                "Nefta",
                "Douz",
                "Kebili"
            ),
            correctIndex = 0,
            funFact = "Famous for its locally-made yellow bricks arranged in geometric patterns, a tradition dating back to the 14th century."
        ),
        Question(
            id = "cit_h_02",
            category = Category.CITIES,
            difficulty = Difficulty.HARD,
            wikipediaTitle = "Nefta",
            imageSource = "Wikimedia Commons",
            prompt = "Which oasis town with a sunken palm grove called the Corbeille is this?",
            options = listOf(
                "Nefta",
                "Tozeur",
                "Douz",
                "Kebili"
            ),
            correctIndex = 0,
            funFact = "Considered a sacred center of Sufi mysticism, with over a hundred zaouias and a striking sunken palm grove called the Corbeille."
        ),
        Question(
            id = "cit_h_03",
            category = Category.CITIES,
            difficulty = Difficulty.HARD,
            wikipediaTitle = "Le_Kef",
            imageSource = "Wikimedia Commons",
            prompt = "Which clifftop town in northwestern Tunisia is this?",
            options = listOf(
                "Le Kef",
                "Beja",
                "Siliana",
                "Jendouba"
            ),
            correctIndex = 0,
            funFact = "Perched on a cliff with a kasbah built atop a Roman fort, layered with Numidian, Roman, Byzantine, and Ottoman history."
        ),
        Question(
            id = "cit_h_04",
            category = Category.CITIES,
            difficulty = Difficulty.HARD,
            wikipediaTitle = "Houmt_Souk",
            imageSource = "Wikimedia Commons",
            prompt = "Which island town, with whitewashed houses and an old port, is this?",
            options = listOf(
                "Houmt Souk (Djerba)",
                "Mahdia",
                "Tabarka",
                "Kerkennah Islands"
            ),
            correctIndex = 0,
            funFact = "The main town of Djerba island, home to one of the oldest Jewish communities in North Africa centered around the El Ghriba synagogue."
        )
    )
}
