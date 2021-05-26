package com.intive.repository.database

import com.intive.repository.database.technologies.TechnologyDao
import com.intive.repository.database.technologies.TechnologyEntity


class DatabaseRepository(private val technologyDao: TechnologyDao) {

    fun getAllTechnologies(): List<TechnologyEntity> =
        technologyDao.getAllTechnologies()

    suspend fun insert(technology: TechnologyEntity) {
        technologyDao.insert(technology)
    }

}