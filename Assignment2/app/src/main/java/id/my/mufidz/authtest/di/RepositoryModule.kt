package id.my.mufidz.authtest.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.my.mufidz.authtest.data.UserRepository
import id.my.mufidz.authtest.data.UserRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindsUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository
}