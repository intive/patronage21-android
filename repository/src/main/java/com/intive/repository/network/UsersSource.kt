package com.intive.repository.network

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.intive.repository.Repository
import com.intive.repository.domain.model.User
import kotlin.math.log

const val USERS_STARTING_PAGE_INDEX = 1

class UsersSource(
    private val repository: Repository,
    private val role: String,
    private val group: String?
    ) : PagingSource<Int, User>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        return try {
            val page = params.key ?: USERS_STARTING_PAGE_INDEX
            val usersResponse = repository.getUsersByRole(page = page, role = role, group = group)
            val users = usersResponse.users

            LoadResult.Page(
                data = repository.usersMapper.mapToDomainList(users),
                prevKey = usersResponse.previousPage,
                nextKey = usersResponse.nextPage
            )
        } catch (e: Exception) {
            Log.e("TAG", "load errror: ", e)
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? = state.anchorPosition
}