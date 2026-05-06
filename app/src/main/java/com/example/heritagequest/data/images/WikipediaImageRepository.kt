package com.example.heritagequest.data.images

import android.content.Context
import com.example.heritagequest.data.repository.ImageRepository

class WikipediaImageRepository(
    private val context: Context
) : ImageRepository {
    override suspend fun resolveImageUrl(questionId: String, wikipediaTitle: String): String? {
        val localPath = "quiz_images/$questionId.jpg"
        val assetUrl = "file:///android_asset/$localPath"

        val hasBundledAsset = runCatching {
            context.assets.open(localPath).use { }
            true
        }.getOrDefault(false)

        if (hasBundledAsset) {
            return assetUrl
        }

        return VerifiedHeritageImageUrls.byWikipediaTitle[wikipediaTitle]
    }
}
