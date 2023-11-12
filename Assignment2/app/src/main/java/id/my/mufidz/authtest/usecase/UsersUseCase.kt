package id.my.mufidz.authtest.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import id.my.mufidz.authtest.base.BaseUseCase
import id.my.mufidz.authtest.data.UserPagingSource
import id.my.mufidz.authtest.data.UserRepository
import id.my.mufidz.authtest.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class UsersUseCase @Inject constructor(private val userRepository: UserRepository) :
    BaseUseCase<Nothing, Flow<PagingData<User>>, Flow<PagingData<User>>>() {

    override suspend fun execute(param: Nothing?): Flow<PagingData<User>> {
        return Pager(
            config = PagingConfig(pageSize = 6, prefetchDistance = 2), pagingSourceFactory = {
                UserPagingSource(userRepository)
            }, initialKey = 1
        ).flow
    }

    override suspend fun Flow<PagingData<User>>.transformToResult(): Flow<PagingData<User>> =
        this.distinctUntilChanged()
}