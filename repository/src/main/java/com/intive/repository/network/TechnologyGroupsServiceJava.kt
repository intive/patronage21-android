package com.intive.repository.network

import retrofit2.http.*

interface TechnologyGroupsServiceJava {
    @GET("api/groups")
    suspend fun getTechnologies(): List<String>
}