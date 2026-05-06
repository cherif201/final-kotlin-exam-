package com.example.heritagequest.ui.navigation

object Routes {
    const val Splash = "splash"
    const val Menu = "menu"
    const val Category = "category"
    const val Difficulty = "difficulty/{categoryId}"
    const val Quiz = "quiz/{categoryId}/{difficulty}"
    const val Results = "results/{categoryId}/{difficulty}/{score}/{total}/{correct}"

    const val ArgCategoryId = "categoryId"
    const val ArgDifficulty = "difficulty"
    const val ArgScore = "score"
    const val ArgTotal = "total"
    const val ArgCorrect = "correct"

    fun difficulty(categoryId: String): String = "difficulty/$categoryId"

    fun quiz(categoryId: String, difficulty: String): String =
        "quiz/$categoryId/$difficulty"

    fun results(
        categoryId: String,
        difficulty: String,
        score: Int,
        total: Int,
        correct: Int
    ): String = "results/$categoryId/$difficulty/$score/$total/$correct"
}
