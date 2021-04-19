package com.intive.repository.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.intive.repository.Repository
import com.intive.repository.domain.model.User


class CandidatesSource(private val repository: Repository) : PagingSource<Int, User>() {
    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        return try {
            val page = params.key ?: 1
            val usersResponse = repository.getCandidates(page = page) //TODO: make role not hardcoded
            LoadResult.Page(
                data = usersResponse,
                prevKey = if (page == 1) null else page - 1,
                nextKey = page.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}