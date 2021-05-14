package com.intive.repository.network

import com.intive.repository.network.model.TechnologiesList
import retrofit2.http.*

interface TechnologyGroupsServiceJava {
    @GET("api/groups")
    suspend fun getTechnologies(): TechnologiesList
}