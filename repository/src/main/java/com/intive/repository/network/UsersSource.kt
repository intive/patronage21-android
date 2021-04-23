package com.intive.repository.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.intive.repository.domain.model.User
import com.intive.repository.network.util.UserDtoMapper

const val USERS_STARTING_PAGE_INDEX = 1

class UsersSource(
    private val networkRepository: NetworkRepository,
    private val usersMapper: UserDtoMapper,
    private val role: String
    ) : PagingSource<Int, User>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        return try {
            val page = params.key ?: USERS_STARTING_PAGE_INDEX
            val usersResponse = networkRepository.getUsersByRole(role = role, page = page)
            val users = usersResponse.users

            LoadResult.Page(
                data = usersMapper.mapToDomainList(users),
                prevKey = if (page == USERS_STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (users.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? = state.anchorPosition
}