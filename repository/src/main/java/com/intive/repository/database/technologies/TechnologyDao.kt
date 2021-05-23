package com.intive.repository.database.technologies

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TechnologyDao {
    @Query("SELECT * FROM technologies_table ORDER BY id ASC")
    fun getAllTechnologies(): List<TechnologyEntity>

    @Insert
    suspend fun insert(technology: TechnologyEntity)
}