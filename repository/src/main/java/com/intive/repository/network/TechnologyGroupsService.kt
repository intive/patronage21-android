package com.intive.repository.network

import com.intive.repository.domain.model.GroupParcelable
import retrofit2.http.*

interface TechnologyGroupsService {
    @GET("api/groups/technologies")
    suspend fun getTechnologies(): List<String>

    @GET("api/groups")
    suspend fun getTechnologyGroups(): List<GroupParcelable>
}