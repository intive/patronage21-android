package com.intive.repository


import com.intive.repository.network.util.EventDtoMapper
import com.intive.repository.network.util.AuditDtoMapper
import com.intive.repository.network.util.NewEventDtoMapper
import com.intive.repository.network.util.UserDtoMapper
import com.intive.repository.util.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder
import com.intive.repository.network.*

private const val BASE_URL = "https://64z31.mocklab.io/"

val repositoryModule = module {
    single<Repository> { RepositoryImpl(get(), get(), get(), get(), get()) }
    single { NetworkRepository(get(), get(), get(), get(), get()) }
    single { createRetrofit() }
    single { createUsersService(get()) }
    single { createUserMapper() }
    single { createTechnologiesService(get()) }
    single { createAuditService(get()) }
    single { createAuditMapper() }
    single { createEventsService(get()) }
    single { createEventsMapper() }
    single { createNewEventsMapper() }
    single { createDispatchers() }
    single { createRegistrationService(get()) }
}

private fun createRetrofit(): Retrofit {

    return Retrofit.Builder()
        .baseUrl(BASE_URL)
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

private fun createTechnologiesService(retrofit: Retrofit): TechnologyGroupsService {
    return retrofit.create(TechnologyGroupsService::class.java)
}

private fun createUserMapper(): UserDtoMapper = UserDtoMapper()

private fun createEventsService(retrofit: Retrofit): EventsService {
    return retrofit.create(EventsService::class.java)
}


private fun createNewEventsMapper(): NewEventDtoMapper = NewEventDtoMapper()

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

