package com.example.heritagequest.data.repository

import com.example.heritagequest.domain.model.Category
import com.example.heritagequest.domain.model.Difficulty
import com.example.heritagequest.domain.model.Question

interface QuestionRepository {
    fun getAllQuestions(): List<Question>
    fun getQuestions(category: Category, difficulty: Difficulty): List<Question>
}
