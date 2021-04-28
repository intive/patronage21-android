package com.intive.repository.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.intive.repository.domain.model.Gradebook
import com.intive.repository.network.util.GradebookDtoMapper

const val GRADEBOOK_STARTING_PAGE_INDEX = 1

class GradebookSource(
    private val networkRepository: NetworkRepository,
    private val gradebookMapper: GradebookDtoMapper,
    ) : PagingSource<Int, Gradebook>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Gradebook> {
        return try {
            val page = params.key ?: GRADEBOOK_STARTING_PAGE_INDEX
            val gradebookResponse = networkRepository.getGradebook(page = page)
            val gradebook = gradebookResponse.gradebook
            LoadResult.Page(
                data = gradebookMapper.mapToDomainList(gradebook),
                prevKey = gradebookResponse.previousPage,
                nextKey = gradebookResponse.nextPage
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Gradebook>): Int? = state.anchorPosition
}