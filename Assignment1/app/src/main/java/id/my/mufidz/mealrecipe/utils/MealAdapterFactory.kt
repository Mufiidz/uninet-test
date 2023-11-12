package id.my.mufidz.mealrecipe.utils

import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.converter.Converter
import de.jensklingenberg.ktorfit.internal.TypeData
import id.my.mufidz.mealrecipe.data.DataResult
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import timber.log.Timber

class MealAdapterFactory : Converter.Factory {

    override fun suspendResponseConverter(
        typeData: TypeData, ktorfit: Ktorfit
    ): Converter.SuspendResponseConverter<HttpResponse, *>? {
        if (typeData.typeInfo.type != DataResult::class) return null
        return object : Converter.SuspendResponseConverter<HttpResponse, DataResult<Any>> {
            override suspend fun convert(response: HttpResponse): DataResult<Any> {
                return try {
                    val result = response.body<Any>(typeData.typeArgs.first().typeInfo)
                    DataResult.Success(result)
                } catch (t: Throwable) {
                    if (response.status == HttpStatusCode.NotFound) {
                        DataResult.Failure(Throwable(response.status.description))
                    } else {
                        DataResult.Failure(t)
                    }
                }
            }
        }
    }
}