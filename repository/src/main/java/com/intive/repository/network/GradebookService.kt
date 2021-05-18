package com.intive.repository.network

import com.intive.repository.network.response.GradebookResponse
import retrofit2.http.GET
import retrofit2.http.Query

const val GRADEBOOK_PAGE_SIZE = 48

interface GradebookService {

    @GET("api/gradebook")
    suspend fun getGradebook(
        @Query("group") group: String,
        @Query("sortby") sortby: String,
        @Query("page") page: Int,
    ): GradebookResponse

}
