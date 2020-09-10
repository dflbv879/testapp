package com.example.testapp.data.entities.todo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todos")
data class TodoResponseItem(
    val completed: Boolean,
    @PrimaryKey
    @ColumnInfo(name = "todo_id")
    val id: Int,
    val title: String,
    val userId: Int
)