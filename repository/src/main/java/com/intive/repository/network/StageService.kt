package com.intive.repository.network

import com.intive.repository.network.model.StageDto
import retrofit2.http.GET
import retrofit2.http.Query

interface StageService {

    @GET("api/stages")
    suspend fun getStages(
        @Query("groupId") groupId: String
    ): List<StageDto>
}