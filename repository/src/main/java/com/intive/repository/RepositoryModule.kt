package com.intive.repository


import android.app.Application
import android.content.SharedPreferences
import androidx.room.Room
import com.intive.repository.network.util.EventDtoMapper
import com.intive.repository.network.util.AuditDtoMapper
import com.intive.repository.network.util.EventInviteResponseDtoMapper
import com.intive.repository.network.util.NewEventDtoMapper
import com.intive.repository.network.util.UserDtoMapper
import com.intive.repository.util.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder
import com.intive.repository.database.Database
import com.intive.repository.database.DatabaseRepository
import com.intive.repository.database.EventLogger
import com.intive.repository.database.util.AuditEntityMapper
import com.intive.repository.local.LocalRepository
import com.intive.repository.local.SharedPreferenceSource
import com.intive.repository.network.*
import org.koin.android.ext.koin.androidApplication
import com.intive.repository.network.util.*
import org.koin.core.qualifier.named
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val BASE_URL = "https://64z31.mocklab.io/"
private const val BASE_URL_JAVA = "http://intive-patronage.pl/"
private const val BASE_URL_JS = "https://api-patronage21.herokuapp.com/"
private const val DATABASE_NAME = "mainDatabase"

val repositoryModule = module {
    single<Repository> { RepositoryImpl(get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get()) }
    single { NetworkRepository(get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get()) }
    single(named("mocklab")) { createRetrofit() }
    single { createUsersService(get((named("mocklab")))) }
    single { createUserMapper() }
    single { createTechnologiesService(get(named("mocklab"))) }
    single { createAuditService(get((named("mocklab")))) }
    single { createAuditMapper() }
    single { createAuditEntityMapper() }
    single { createEventsService(get((named("mocklab")))) }
    single { createEventsMapper() }
    single { createEventInviteResponseMapper() }
    single { createNewEventsMapper() }
    single { createEditEventsMapper() }
    single { createDispatchers() }
    single { createRegistrationService(get((named("mocklab")))) }
    single { createRegistrationServiceJava(get(named("java"))) }
    single(named("java")){ createRetrofit2() }
    single { createTechnologiesJavaService(get(named("java"))) }
    single { createStageService(get((named("mocklab")))) }
    single { createStageMapper() }
    single { createStageDetailsService(get((named("mocklab")))) }
    single { createStageDetailsMapper() }
    single { createGradebookService(get((named("mocklab")))) }
    single { createGradebookMapper() }
    single { provideSharedPref(androidApplication()) }
    single { LocalRepository(get())}
    single { SharedPreferenceSource(get()) }
    single(named("js")){ createRetrofitJS() }
    single { createEventsJSService(get(named("js"))) }
    single { createEventLogger(get()) }
}

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            Database::class.java,
            DATABASE_NAME
        ).build()
    }

    single {
        get<Database>().technologyDao()
    }
    single {
        get<Database>().auditDao()
    }

    single {
        DatabaseRepository(technologyDao = get(), auditDao = get())
    }
}

private fun createRetrofit(): Retrofit {

    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .build()
}

private fun createRetrofit2(): Retrofit {

    return Retrofit.Builder()
        .baseUrl(BASE_URL_JAVA)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .build()
}

private fun createRetrofitJS(): Retrofit {

    return Retrofit.Builder()
        .baseUrl(BASE_URL_JS)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .build()
}

private fun createUsersService(retrofit: Retrofit): UsersService {
    return retrofit.create(UsersService::class.java)
}

private fun createEventsMapper(): EventDtoMapper = EventDtoMapper()

private fun createAuditService(retrofit: Retrofit): AuditService {
    return retrofit.create(AuditService::class.java)
}

private fun createAuditMapper(): AuditDtoMapper = AuditDtoMapper()

private fun createAuditEntityMapper(): AuditEntityMapper = AuditEntityMapper()

private fun createTechnologiesService(retrofit: Retrofit): TechnologyGroupsService {
    return retrofit.create(TechnologyGroupsService::class.java)
}

private fun createUserMapper(): UserDtoMapper = UserDtoMapper()

private fun createEventsService(retrofit: Retrofit): EventsService {
    return retrofit.create(EventsService::class.java)
}

private fun createEventsJSService(retrofit: Retrofit): EventsServiceJS {
    return retrofit.create(EventsServiceJS::class.java)
}

private fun createEventInviteResponseMapper(): EventInviteResponseDtoMapper = EventInviteResponseDtoMapper()

private fun createNewEventsMapper(): NewEventDtoMapper = NewEventDtoMapper()

private fun createEditEventsMapper(): EditEventDtoMapper = EditEventDtoMapper()

fun createDispatchers(): DispatcherProvider = object : DispatcherProvider {
    override val main: CoroutineDispatcher
        get() = Dispatchers.Main
    override val io: CoroutineDispatcher
        get() = Dispatchers.IO
    override val default: CoroutineDispatcher
        get() = Dispatchers.Default
    override val unconfined: CoroutineDispatcher
        get() = Dispatchers.Unconfined
}

private fun createRegistrationService(retrofit: Retrofit): RegistrationService {
    return retrofit.create(RegistrationService::class.java)
}

private fun createRegistrationServiceJava(retrofit: Retrofit): RegistrationServiceJava {
    return retrofit.create(RegistrationServiceJava::class.java)
}

private fun createTechnologiesJavaService(retrofit: Retrofit): TechnologyGroupsServiceJava {
    return retrofit.create(TechnologyGroupsServiceJava::class.java)
}

private fun createStageMapper(): StageDtoMapper = StageDtoMapper()

private fun createStageService(retrofit: Retrofit): StageService {
    return retrofit.create(StageService::class.java)
}

private fun createStageDetailsMapper(): StageDetailsDtoMapper = StageDetailsDtoMapper()

private fun createStageDetailsService(retrofit: Retrofit): StageDetailsService {
    return retrofit.create(StageDetailsService::class.java)
}

private fun createGradebookService(retrofit: Retrofit): GradebookService {
    return retrofit.create(GradebookService::class.java)
}

private fun createGradebookMapper(): GradebookDtoMapper = GradebookDtoMapper()

private fun createEventLogger(repository: Repository): EventLogger = EventLogger(repository)

fun provideSharedPref(app: Application): SharedPreferences {
    return androidx.preference.PreferenceManager.getDefaultSharedPreferences(app)
}
