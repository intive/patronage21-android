package com.intive.repository.database

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

    fun getAllAudits(): List<AuditEntity> =
        auditDao.getAllAudits()

    suspend fun searchAuditsAsc(query: String, loadSize: Int): List<AuditEntity> =
        auditDao.searchAuditsAsc(query, loadSize)

    suspend fun searchAuditsDesc(query: String, loadSize: Int): List<AuditEntity> =
        auditDao.searchAuditsDesc(query, loadSize)

    suspend fun insert(audit: AuditEntity){
        auditDao.insert(audit)
    }

    suspend fun getAuditsCount(): Int  {
        return auditDao.getCount()
    }

}