package com.intive.repository.network

import retrofit2.http.GET

interface TechnologyGroupsService {
    @GET("api/tech_groups.json")
    suspend fun getTechGroups(): List<String>
}