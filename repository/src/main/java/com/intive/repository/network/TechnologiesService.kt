package com.intive.repository.network

import retrofit2.http.GET

interface TechnologiesService {
    @GET("api/tech_groups.json")
    suspend fun getTechnologies(): List<String>
}