package id.my.mufidz.mealrecipe.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.my.mufidz.mealrecipe.data.local.MealLocalRepository
import id.my.mufidz.mealrecipe.data.local.MealLocalRepositoryImpl
import id.my.mufidz.mealrecipe.data.network.MealRepository
import id.my.mufidz.mealrecipe.data.network.MealRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindsMealRepository(mealRepositoryImpl: MealRepositoryImpl): MealRepository

    @Binds
    fun bindsMealLocalRepository(mealLocalRepositoryImpl: MealLocalRepositoryImpl): MealLocalRepository
}