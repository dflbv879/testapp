package com.example.testapp.data.remote

import javax.inject.Inject

class AppRemoteDataSource @Inject constructor(
    private val appService: AppService
): BaseDataSource() {

    suspend fun getTodos() = getResult { appService.getTodos() }
    suspend fun getUsers() = getResult { appService.getUsers() }
}