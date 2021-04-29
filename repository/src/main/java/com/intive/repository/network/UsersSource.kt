package com.intive.repository.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.intive.repository.Repository
import com.intive.repository.domain.model.User

const val USERS_STARTING_PAGE_INDEX = 1

class UsersSource(
    private val repository: Repository,
    private val role: String
    ) : PagingSource<Int, User>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        return try {
            val page = params.key ?: USERS_STARTING_PAGE_INDEX
            val usersResponse = repository.getUsersByRole(role = role, page = page)
            val users = usersResponse.users

            LoadResult.Page(
                data = repository.usersMapper.mapToDomainList(users),
                prevKey = usersResponse.previousPage,
                nextKey = usersResponse.nextPage
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? = state.anchorPosition
}