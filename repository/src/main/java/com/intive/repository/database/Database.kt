package com.intive.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.intive.repository.database.audits.AuditDao
import com.intive.repository.database.audits.AuditEntity
import com.intive.repository.database.technologies.TechnologyDao
import com.intive.repository.database.technologies.TechnologyEntity

@Database(entities = [TechnologyEntity::class, AuditEntity::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun technologyDao(): TechnologyDao
    abstract fun auditDao(): AuditDao
}