package com.intive.repository.database.audits

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.intive.repository.Repository
import com.intive.repository.domain.model.Audit

class AuditsPagingSource(
    private val repository: Repository,
    private val query: String = "",
    private val sortBy: String
) : PagingSource<Int, Audit>() {
    private companion object {
        const val INITIAL_PAGE_INDEX = 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Audit> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val auditResponse =
                if(query.isEmpty()){
                    when (sortBy) {
                        "desc" -> repository.getAllAuditsDesc(params.loadSize)
                        else -> repository.getAllAuditsAsc(params.loadSize)
                    }
                }else{
                    when (sortBy) {
                        "desc" -> repository.searchAuditsDesc(query, params.loadSize)
                        else -> repository.searchAuditsAsc(query, params.loadSize)
                    }
                }

            LoadResult.Page(
                data = repository.auditsEntityMapper.toModelList(auditResponse),
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (auditResponse.isNullOrEmpty()) null else position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, Audit>): Int? = null
}