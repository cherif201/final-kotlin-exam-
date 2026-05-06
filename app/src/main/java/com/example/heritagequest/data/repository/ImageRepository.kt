package com.example.heritagequest.data.repository

interface ImageRepository {
    suspend fun resolveImageUrl(questionId: String, wikipediaTitle: String): String?
}
