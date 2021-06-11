package com.intive.repository.database

import androidx.paging.PagingSource
import com.intive.repository.database.audits.AuditDao
import com.intive.repository.database.audits.AuditEntity
import com.intive.repository.database.technologies.TechnologyDao
import com.intive.repository.database.technologies.TechnologyEntity


class DatabaseRepository(
    private val technologyDao: TechnologyDao,
    private val auditDao: AuditDao
    ) {

    fun getAllTechnologies(): List<TechnologyEntity> =
        technologyDao.getAllTechnologies()

    suspend fun insert(technology: TechnologyEntity) {
        technologyDao.insert(technology)
    }

    suspend fun clearTechnologiesTable() = technologyDao.clearTechnologiesTable()

    suspend fun getTechnologiesCount(): Int  {
        return technologyDao.getCount()
    }

    fun getAllAuditsAsc(): PagingSource<Int, AuditEntity> =
        auditDao.getAllAuditsAsc()

    fun getAllAuditsDesc(): PagingSource<Int, AuditEntity> =
        auditDao.getAllAuditsDesc()

    fun searchAuditsAsc(query: String): PagingSource<Int, AuditEntity> =
        auditDao.searchAuditsAsc(query)

    fun searchAuditsDesc(query: String): PagingSource<Int, AuditEntity> =
        auditDao.searchAuditsDesc(query)

    suspend fun insert(audit: AuditEntity){
        auditDao.insert(audit)
    }
}