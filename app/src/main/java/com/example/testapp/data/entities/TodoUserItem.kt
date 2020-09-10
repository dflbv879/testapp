package com.example.testapp.data.entities

import androidx.room.Embedded
import androidx.room.Relation
import com.example.testapp.data.entities.todo.TodoResponseItem
import com.example.testapp.data.entities.user.UserResponseItem

class TodoUserItem {
    @Embedded
    var todo: TodoResponseItem? = null

    @Relation(parentColumn =  "userId", entityColumn = "user_id")
    var user: UserResponseItem? = null
}