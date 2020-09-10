package com.example.testapp.ui.user

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.testapp.data.entities.user.UserResponseItem
import com.example.testapp.data.repository.UserRepository
import com.example.testapp.utils.Resource

class UserViewModel @ViewModelInject constructor(
    private val repository: UserRepository
) : ViewModel() {

    private val _id = MutableLiveData<Int>()

    private val _user = _id.switchMap { id ->
        repository.getUserLocal(id)
    }
    val user: LiveData<Resource<UserResponseItem>> = _user


    fun start(id: Int) {
        _id.value = id
    }
}
