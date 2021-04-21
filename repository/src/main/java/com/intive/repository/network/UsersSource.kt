package com.intive.repository.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.intive.repository.Repository
import com.intive.repository.domain.model.User
import com.intive.repository.network.util.UserDtoMapper


class CandidatesSource(
    private val networkRepository: NetworkRepository,
    private val usersMapper: UserDtoMapper,
    private val role: String
    ) : PagingSource<Int, User>() {

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        return try {
            val page = params.key ?: 1
            val usersResponse = networkRepository.getUsersByRole(role = role, page = page)
            LoadResult.Page(
                data = usersMapper.mapToDomainList(usersResponse.users),
                prevKey = if (page == 1) null else page - 1,
                nextKey = page.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}