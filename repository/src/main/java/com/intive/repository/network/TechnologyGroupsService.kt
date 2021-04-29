package com.intive.repository.network

import retrofit2.http.GET

interface TechnologyGroupsService {
    @GET("api/groups")
    suspend fun getTechGroups(): List<String>
}