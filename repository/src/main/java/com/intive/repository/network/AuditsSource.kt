package com.intive.repository.network

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.intive.repository.Repository
import com.intive.repository.domain.model.Audit
import com.intive.repository.domain.model.User

const val AUDITS_STARTING_PAGE_INDEX = 1

class AuditsSource(
    private val repository: Repository,
    private val query: String = ""
) : PagingSource<Int, Audit>() {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Audit> {
        return try {
            val page = params.key ?: AUDITS_STARTING_PAGE_INDEX
            val auditResponse = repository.searchAudits(page = page, query = query)
            val audits = auditResponse.audits

            LoadResult.Page(
                data = repository.auditsMapper.toDomainList(audits),
                prevKey = auditResponse.previousPage,
                nextKey = auditResponse.nextPage
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Audit>): Int? = state.anchorPosition
}