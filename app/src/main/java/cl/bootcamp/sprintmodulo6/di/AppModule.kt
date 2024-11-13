package cl.bootcamp.sprintmodulo6.di

import android.content.Context
import androidx.room.Room
import cl.bootcamp.sprintmodulo6.data.ApiService
import cl.bootcamp.sprintmodulo6.data.RetrofitInstance
import cl.bootcamp.sprintmodulo6.room.AppDatabase
import cl.bootcamp.sprintmodulo6.room.ProductDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApiService(): ApiService {
        return RetrofitInstance.api
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "products_db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideProductDao(database: AppDatabase): ProductDao {
        return database.productDao()
    }
}