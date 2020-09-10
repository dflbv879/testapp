package com.example.testapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.testapp.data.entities.TodoUserItem
import com.example.testapp.data.entities.user.UserResponseItem

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllUsers(todos: List<UserResponseItem>)

    @Query("SELECT * FROM user WHERE user_id=:id")
    fun getUser(id: Int) : LiveData<UserResponseItem>

    @Query("SELECT * FROM user")
    fun getAllUsers() : LiveData<UserResponseItem>

}