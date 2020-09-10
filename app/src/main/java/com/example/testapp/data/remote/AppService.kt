package com.example.testapp.data.remote

import com.example.testapp.data.entities.todo.TodoResponseItem
import com.example.testapp.data.entities.user.UserResponseItem
import retrofit2.Response
import retrofit2.http.GET

interface AppService {

    @GET("todos")
    suspend fun getTodos() : Response<List<TodoResponseItem>>

    @GET("users")
    suspend fun getUsers() : Response<List<UserResponseItem>>

}