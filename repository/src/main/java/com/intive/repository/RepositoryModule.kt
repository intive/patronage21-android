package com.intive.repository

import com.intive.repository.network.NetworkRepository
import com.intive.repository.network.UsersService
import com.intive.repository.network.util.UserDtoMapper
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://64z31.mocklab.io/"


val repositoryModule = module {
    single<Repository> { RepositoryImpl(get(), get()) }
    single { NetworkRepository(get()) }
    single { createRetrofit() }
    single { createUsersService(get()) }
    single { createUserMapper() }
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
