package id.my.mufidz.mealrecipe.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.my.mufidz.mealrecipe.data.local.ListIngredientConverter
import id.my.mufidz.mealrecipe.data.local.MealRecipeDao
import id.my.mufidz.mealrecipe.data.local.MealRecipeDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private const val DB_NAME: String = "meal_recipe"

    @Provides
    @Singleton
    fun providesPokeDatabase(application: Application): MealRecipeDatabase =
        Room.databaseBuilder(application, MealRecipeDatabase::class.java, DB_NAME)
            .addTypeConverter(ListIngredientConverter())
            .fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun providesMealDao(mealRecipeDatabase: MealRecipeDatabase): MealRecipeDao =
        mealRecipeDatabase.mealDao()
}