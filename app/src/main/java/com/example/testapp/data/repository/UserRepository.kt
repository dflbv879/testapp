package com.example.testapp.data.repository

import androidx.lifecycle.LiveData
import com.example.testapp.data.entities.TodoUserItem
import com.example.testapp.data.entities.user.UserResponseItem
import com.example.testapp.data.local.UserDao
import com.example.testapp.data.remote.AppRemoteDataSource
import com.example.testapp.utils.performGetOperation
import com.example.testapp.utils.performLocalGetOperation
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val remoteDataSource: AppRemoteDataSource,
    private val localDataSource: UserDao
) {

    fun getAllUsers() = performGetOperation(
        databaseQuery = { localDataSource.getAllUsers()},
        networkCall = { remoteDataSource.getUsers() },
        saveCallResult = { localDataSource.insertAllUsers(it) }
    )

    fun getUserLocal(id: Int) = performLocalGetOperation(
        databaseQuery = { localDataSource.getUser(id)}
    )

}