package id.my.mufidz.authtest.data

import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Header
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.Query
import id.my.mufidz.authtest.model.UserDto

interface UserApiServices {

    @POST("login")
    suspend fun login(@Body user: UserDto): LoginResponse

    @GET("users")
    suspend fun getUsers(@Query("page") page: Int = 1, ): DataResult<UsersResponse>
}