package com.intive.repository.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.intive.repository.Repository
import com.intive.repository.domain.model.User

const val AUDITS_STARTING_PAGE_INDEX = 1

class AuditsSource(
    private val repository: Repository,
) : PagingSource<Int, User>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        return try {
            val page = params.key ?: AUDITS_STARTING_PAGE_INDEX
            val auditResponse = repository.searchAudits(page = page, query = "")
            val audits = auditResponse.audits

            LoadResult.Page(
                data = repository.usersMapper.mapToDomainList(users),
                prevKey = auditResponse.previousPage,
                nextKey = auditResponse.nextPage
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? = state.anchorPosition
}