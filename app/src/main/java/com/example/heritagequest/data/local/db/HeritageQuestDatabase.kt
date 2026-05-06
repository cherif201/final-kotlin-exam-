package com.example.heritagequest.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PlayerStatsEntity::class], version = 1)
abstract class HeritageQuestDatabase : RoomDatabase() {
    abstract fun playerStatsDao(): PlayerStatsDao
}
