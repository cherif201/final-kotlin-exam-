package com.example.heritagequest.data.questions

import com.example.heritagequest.data.repository.QuestionRepository
import com.example.heritagequest.domain.model.Category
import com.example.heritagequest.domain.model.Difficulty
import com.example.heritagequest.domain.model.Question

class LocalQuestionRepository : QuestionRepository {
    override fun getAllQuestions(): List<Question> = QuestionBank.all()

    override fun getQuestions(category: Category, difficulty: Difficulty): List<Question> =
        QuestionBank.forCategoryAndDifficulty(category, difficulty)
}
