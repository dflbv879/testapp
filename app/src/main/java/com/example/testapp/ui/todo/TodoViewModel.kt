package com.example.testapp.ui.todo

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.testapp.data.repository.TodoRepository

class TodoViewModel @ViewModelInject constructor(
    private val repository: TodoRepository) : ViewModel() {

    val userTodos = repository.getLocalTodos()

}
