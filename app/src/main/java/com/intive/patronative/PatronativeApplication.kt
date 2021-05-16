package com.intive.patronative

import android.app.Application
import com.intive.registration.registrationModule
import com.intive.calendar.calendarModule
import com.intive.audit.auditModule
import com.intive.repository.repositoryModule
import com.intive.tech_groups.techGroupsModule
import com.intive.users.usersModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin


class PatronativeApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@PatronativeApplication)
            modules(
                repositoryModule,
                auditModule, 
                calendarModule,
                registrationModule,
                usersModule,
                techGroupsModule
            )
        }
    }
}