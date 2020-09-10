package com.example.testapp.data.repository

import androidx.lifecycle.LiveData
import com.example.testapp.data.entities.TodoUserItem
import com.example.testapp.data.local.TodoDao
import com.example.testapp.data.remote.AppRemoteDataSource
import com.example.testapp.utils.performGetOperation
import com.example.testapp.utils.performLocalGetOperation
import javax.inject.Inject

class TodoRepository @Inject constructor(
    private val remoteDataSource: AppRemoteDataSource,
    private val localDataSource: TodoDao
) {


    fun getTodo() = performGetOperation(
        databaseQuery = { localDataSource.getAllTodos() },
        networkCall = { remoteDataSource.getTodos() },
        saveCallResult = { localDataSource.insertAllTodos(it) }
    )

    fun getLocalTodos() = performLocalGetOperation(
        databaseQuery = { localDataSource.getUserTodos() }
    )
}