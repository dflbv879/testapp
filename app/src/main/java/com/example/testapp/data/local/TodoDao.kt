package com.example.testapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.testapp.data.entities.TodoUserItem
import com.example.testapp.data.entities.todo.TodoResponseItem

@Dao
interface TodoDao {

    @Query("SELECT * FROM todos")
    fun getAllTodos(): LiveData<List<TodoResponseItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTodos(todos: List<TodoResponseItem>)

    @Query("SELECT * FROM todos")
    fun getUserTodos(): LiveData<List<TodoUserItem>>

}
