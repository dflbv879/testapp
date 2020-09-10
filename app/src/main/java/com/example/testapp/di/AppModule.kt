package com.example.testapp.di

import android.content.Context
import com.example.testapp.data.local.AppDatabase
import com.example.testapp.data.local.TodoDao
import com.example.testapp.data.local.UserDao
import com.example.testapp.data.remote.AppRemoteDataSource
import com.example.testapp.data.remote.AppService
import com.example.testapp.data.repository.TodoRepository
import com.example.testapp.data.repository.UserRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideAppService(retrofit: Retrofit): AppService = retrofit.create(AppService::class.java)

    @Singleton
    @Provides
    fun provideRemoteDataSource(appService: AppService) = AppRemoteDataSource(appService)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideTodoDao(db: AppDatabase) = db.todoDao()

    @Singleton
    @Provides
    fun provideUserDao(db: AppDatabase) = db.userDao()

    @Singleton
    @Provides
    fun provideTodoRepository(remoteDataSource: AppRemoteDataSource,
                              localDataSource: UserDao
    ) = UserRepository(remoteDataSource, localDataSource)

    @Singleton
    @Provides
    fun provideRepository(remoteDataSource: AppRemoteDataSource,
                          localDataSource: TodoDao
    ) = TodoRepository(remoteDataSource, localDataSource)
}