package id.my.mufidz.authtest.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.my.mufidz.authtest.data.UserRepository
import id.my.mufidz.authtest.usecase.LoginUseCase
import id.my.mufidz.authtest.usecase.UsersUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Singleton
    @Provides
    fun provideLoginUseCase(userRepository: UserRepository): LoginUseCase =
        LoginUseCase(userRepository)

    @Singleton
    @Provides
    fun provideUsersUseCase(userRepository: UserRepository): UsersUseCase =
        UsersUseCase(userRepository)
}