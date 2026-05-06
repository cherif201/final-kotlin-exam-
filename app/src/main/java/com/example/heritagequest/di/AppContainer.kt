package com.example.heritagequest.di

import android.content.Context
import androidx.room.Room
import com.example.heritagequest.data.images.WikipediaImageRepository
import com.example.heritagequest.data.local.datastore.DataStoreSettingsRepository
import com.example.heritagequest.data.local.db.HeritageQuestDatabase
import com.example.heritagequest.data.questions.LocalQuestionRepository
import com.example.heritagequest.data.stats.RoomStatsRepository

class AppContainer(context: Context) {
    private val database: HeritageQuestDatabase =
        Room.databaseBuilder(
            context,
            HeritageQuestDatabase::class.java,
            "heritagequest.db"
        ).build()

    val questionRepository = LocalQuestionRepository()
    val statsRepository = RoomStatsRepository(database.playerStatsDao())
    val settingsRepository = DataStoreSettingsRepository(context)
    val imageRepository = WikipediaImageRepository(context)
}
