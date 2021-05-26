package com.intive.repository.database.technologies

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "technologies_table")
data class TechnologyEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo val name: String
)