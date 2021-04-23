package com.intive.repository

import com.intive.repository.network.AuditService
import com.intive.repository.network.NetworkRepository
import com.intive.repository.network.TechnologyGroupsService
import com.intive.repository.network.UsersService
import com.intive.repository.network.util.AuditDtoMapper
import com.intive.repository.network.util.UserDtoMapper
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

private const val BASE_URL = "https://64z31.mocklab.io/"


val repositoryModule = module {
  
    single<Repository> { RepositoryImpl(get(), get(), get()) }
    single { NetworkRepository(get(), get()) }
    single { createRetrofit() }
    single { createUsersService(get()) }
    single { createUserMapper() }
    single { createTechnologyGroupsService(get()) }
    single { createAuditService(get()) }
    single { createAuditMapper() }
}

private fun createRetrofit(): Retrofit {

    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

private fun createUsersService(retrofit: Retrofit): UsersService {
    return retrofit.create(UsersService::class.java)
}

private fun createUserMapper(): UserDtoMapper = UserDtoMapper()

private fun createTechnologyGroupsService(retrofit: Retrofit): TechnologyGroupsService {
    return retrofit.create(TechnologyGroupsService::class.java)
}

private fun createAuditService(retrofit: Retrofit): AuditService {
    return retrofit.create(AuditService::class.java)
}

private fun createAuditMapper(): AuditDtoMapper = AuditDtoMapper()