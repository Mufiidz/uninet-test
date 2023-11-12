package id.my.mufidz.authtest.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import id.my.mufidz.authtest.model.User

class UserPagingSource(private val userRepository: UserRepository) : PagingSource<Int, User>() {
    override fun getRefreshKey(state: PagingState<Int, User>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> = try {
        val currentPage = params.key ?: 0
        when (val dataSource = userRepository.getUsers(currentPage)) {
            is DataResult.Failure -> LoadResult.Error(dataSource.error)
            is DataResult.Success -> {
                val users = dataSource.data.data
                LoadResult.Page(
                    data = users,
                    prevKey = null,
                    nextKey = if (users.isEmpty()) null else currentPage + 1
                )
            }
        }
    } catch (e: Exception) {
        LoadResult.Error(e)
    }
}