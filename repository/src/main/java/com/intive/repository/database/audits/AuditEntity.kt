package com.intive.repository.database.audits

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "audits_table")
data class AuditEntity (
    @PrimaryKey(autoGenerate = true) val id: Long,
    val title: String?,
    val date: String?,
    @ColumnInfo(name="user_name") val userName: String?
)