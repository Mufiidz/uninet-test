package id.my.mufidz.mealrecipe.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import de.jensklingenberg.ktorfit.Ktorfit
import id.my.mufidz.mealrecipe.data.network.MealApiServices
import id.my.mufidz.mealrecipe.utils.MealAdapterFactory
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.addDefaultResponseValidation
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val LOGGING = "Network Request"

    @Singleton
    @Provides
    fun provideBaseHttpClient(): HttpClient = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                    coerceInputValues = true

                }, ContentType.Application.Json
            )
        }
        install(Logging) {
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    Timber.d(message)
                }
            }
        }
        addDefaultResponseValidation()
        defaultRequest {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }
    }

    @Singleton
    @Provides
    fun provideKtorfit(httpClient: HttpClient): Ktorfit =
        Ktorfit.Builder().httpClient(httpClient).baseUrl("https://www.themealdb.com/api/json/v1/1/", true)
            .converterFactories(MealAdapterFactory()).build()

    @Singleton
    @Provides
    fun provideMealApiServices(ktorfit: Ktorfit): MealApiServices = ktorfit.create()
}