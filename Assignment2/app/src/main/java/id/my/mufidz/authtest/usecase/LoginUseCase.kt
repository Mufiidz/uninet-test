package id.my.mufidz.authtest.usecase

import id.my.mufidz.authtest.base.BaseUseCase
import id.my.mufidz.authtest.data.DataResult
import id.my.mufidz.authtest.data.UserRepository
import id.my.mufidz.authtest.model.UserDto
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val userRepository: UserRepository) :
    BaseUseCase<UserDto, DataResult<String>, LoginUseCase.Result>() {
    override suspend fun execute(param: UserDto?): DataResult<String> {
        if (param == null) return DataResult.Failure(Throwable("Param cant be null"))
        return userRepository.loginUser(param)
    }

    override suspend fun DataResult<String>.transformToResult(): Result = when (this) {
        is DataResult.Failure -> Result.Error(error.localizedMessage ?: error.toString())
        is DataResult.Success -> Result.Success(data)
    }

    sealed class Result {
        data class Success(val message: String) : Result()
        data class Error(val errorMessage: String) : Result()
    }
}