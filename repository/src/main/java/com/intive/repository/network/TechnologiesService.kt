package com.intive.repository.network

import retrofit2.http.GET

interface TechnologiesService {
    @GET("api/groups")
    suspend fun getTechnologies(): List<String>
}