package com.example.testapp.ui.splash

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.testapp.data.repository.TodoRepository
import com.example.testapp.data.repository.UserRepository
import com.example.testapp.utils.zip

class SplashViewModel @ViewModelInject constructor(
    private val todoRepository: TodoRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    val response = zip(todoRepository.getTodo(),userRepository.getAllUsers())

}