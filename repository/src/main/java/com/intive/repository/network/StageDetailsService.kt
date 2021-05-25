package com.intive.repository.network


import com.intive.repository.network.model.StageDetailsDto
import retrofit2.http.*

interface StageDetailsService {

    @GET("api/stages")
    suspend fun getStageDetails(@Query("id") id: Long): StageDetailsDto

}