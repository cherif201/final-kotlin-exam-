package com.example.heritagequest.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerStatsDao {
    @Query("SELECT * FROM player_stats WHERE id = 0")
    fun observeStats(): Flow<PlayerStatsEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(stats: PlayerStatsEntity): Long
}
