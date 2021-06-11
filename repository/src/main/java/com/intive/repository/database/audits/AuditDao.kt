package com.intive.repository.database.audits

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AuditDao {
    @Query("SELECT * FROM audits_table ORDER BY id ASC")
    fun getAllAuditsAsc(): PagingSource<Int, AuditEntity>

    @Query("SELECT * FROM audits_table ORDER BY id DESC")
    fun getAllAuditsDesc(): PagingSource<Int, AuditEntity>

    @Query("SELECT * FROM audits_table WHERE title LIKE:query ORDER BY date ASC")
    fun searchAuditsAsc(query: String): PagingSource<Int, AuditEntity>

    @Query("SELECT * FROM audits_table WHERE title LIKE:query ORDER BY date DESC")
    fun searchAuditsDesc(query: String): PagingSource<Int, AuditEntity>

    @Insert
    suspend fun insert(audit: AuditEntity)

    @Query("SELECT COUNT(*) FROM audits_table")
    suspend fun getCount(): Int
}