package com.intive.repository.network

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.intive.repository.Repository
import com.intive.repository.domain.model.Gradebook

const val GRADEBOOK_STARTING_PAGE_INDEX = 1

class GradebookSource(
    private val repository: Repository,
    private val group: String,
    private val sortby: String
    ) : PagingSource<Int, Gradebook>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Gradebook> {
        return try {
            val page = params.key ?: GRADEBOOK_STARTING_PAGE_INDEX
            val gradebookResponse = repository.getGradebook(group = group, sortby = sortby, page = page)
            val gradebook = gradebookResponse.gradebook

            LoadResult.Page(
                data = repository.gradebookMapper.mapToDomainList(gradebook),
                prevKey = gradebookResponse.previousPage,
                nextKey = gradebookResponse.nextPage
            )
        } catch (e: Exception) {
            Log.e("TAG", "load errror: ", e)
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Gradebook>): Int? = state.anchorPosition
}