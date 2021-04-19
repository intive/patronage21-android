package com.intive.patronative

import android.app.Application
import com.intive.repository.repositoryModule
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
                usersModule
            )
        }
    }
}