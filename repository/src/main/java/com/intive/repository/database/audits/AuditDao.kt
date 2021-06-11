package com.intive.repository.database.audits

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AuditDao {
    @Query("SELECT * FROM audits_table ORDER BY id ASC")
    fun getAllAudits(): List<AuditEntity>

    @Query("SELECT * FROM audits_table WHERE title LIKE:query ORDER BY id ASC LIMIT :loadSize")
    suspend fun searchAuditsAsc(query: String, loadSize: Int): List<AuditEntity>

    @Query("SELECT * FROM audits_table WHERE title LIKE:query ORDER BY id DESC LIMIT :loadSize")
    suspend fun searchAuditsDesc(query: String, loadSize: Int): List<AuditEntity>

    @Insert
    suspend fun insert(audit: AuditEntity)

    @Query("SELECT COUNT(*) FROM audits_table")
    suspend fun getCount(): Int
}