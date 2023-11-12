package id.my.mufidz.authtest.utils

import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.converter.Converter
import de.jensklingenberg.ktorfit.internal.TypeData
import id.my.mufidz.authtest.data.LoginResponse
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse

class LoginResponseAdapterFactory : Converter.Factory {

    override fun suspendResponseConverter(
        typeData: TypeData, ktorfit: Ktorfit
    ): Converter.SuspendResponseConverter<HttpResponse, *>? {
        if (typeData.typeInfo.type != LoginResponse::class) return null
        return object : Converter.SuspendResponseConverter<HttpResponse, LoginResponse> {
            override suspend fun convert(response: HttpResponse): LoginResponse {
                return try {
                    response.body<LoginResponse.Success>()
                } catch (t: Throwable) {
                    response.body<LoginResponse.Failed>()
                }
            }
        }
    }
}