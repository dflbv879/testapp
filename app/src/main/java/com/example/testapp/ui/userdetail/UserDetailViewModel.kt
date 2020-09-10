package com.example.testapp.ui.userdetail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.testapp.data.entities.user.UserResponseItem
import com.example.testapp.data.repository.UserRepository
import com.example.testapp.utils.Resource

class UserDetailViewModel @ViewModelInject constructor(
    private val repository: UserRepository
) : ViewModel() {

    private val _id = MutableLiveData<Int>()

    private val _users = _id.switchMap { id ->
        repository.getUserLocal(id)
    }
    val users: LiveData<Resource<UserResponseItem>> = _users

    fun start(id: Int) {
        _id.value = id
    }

}
