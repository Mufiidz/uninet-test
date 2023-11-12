package id.my.mufidz.authtest.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import id.my.mufidz.authtest.model.UserDto
import javax.inject.Inject

interface UserRepository {
    suspend fun loginUser(user: UserDto): DataResult<String>
    suspend fun getUsers(page: Int): DataResult<UsersResponse>
}

class UserRepositoryImpl @Inject constructor(
    private val userApiServices: UserApiServices,
    private val dataStore: DataStore<Preferences>
) : UserRepository {

    private object PreferenceKeys {
        val TOKEN = stringPreferencesKey("token")
    }

    override suspend fun loginUser(user: UserDto): DataResult<String> {
        return when (val response = userApiServices.login(user)) {
            is LoginResponse.Failed -> DataResult.Failure(Exception(response.error))
            is LoginResponse.Success -> {
                val token = response.token
                dataStore.edit {
                    it[PreferenceKeys.TOKEN] = token
                }
                DataResult.Success(token)
            }
        }
    }

    override suspend fun getUsers(page: Int): DataResult<UsersResponse> =
        userApiServices.getUsers(page)

}