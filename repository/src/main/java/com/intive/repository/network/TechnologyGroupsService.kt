package com.intive.repository.network

import com.intive.repository.domain.model.Group
import retrofit2.http.*

interface TechnologyGroupsService {
    @GET("api/groups/technologies2")
    suspend fun getTechnologies(): List<String>

    @GET("api/groups")
    suspend fun getTechnologyGroups(): List<Group>
}