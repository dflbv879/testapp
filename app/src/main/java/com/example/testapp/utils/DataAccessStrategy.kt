package com.example.testapp.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import kotlinx.coroutines.Dispatchers

fun <T, A> performGetOperation(databaseQuery: () -> LiveData<T>,
                               networkCall: suspend () -> Resource<A>,
                               saveCallResult: suspend (A) -> Unit): LiveData<Resource<T>> =
    liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val source = databaseQuery.invoke().map { Resource.success(it) }
        emitSource(source)

        val responseStatus = networkCall.invoke()
        if (responseStatus.status == Resource.Status.SUCCESS) {
            saveCallResult(responseStatus.data!!)

        } else if (responseStatus.status == Resource.Status.ERROR) {
            emit(Resource.error(responseStatus.message!!))
            emitSource(source)
        }
    }

fun <T> performLocalGetOperation(databaseQuery: () -> LiveData<T>): LiveData<Resource<T>> =
    liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val source = databaseQuery.invoke().map { Resource.success(it) }
        emitSource(source)
    }

fun <A, B> zip(first: LiveData<A>, second: LiveData<B>): LiveData<Pair<A, B>> {
    val mediatorLiveData = MediatorLiveData<Pair<A, B>>()

    var isFirstEmitted = false
    var isSecondEmitted = false
    var firstValue: A? = null
    var secondValue: B? = null

    mediatorLiveData.addSource(first) {
        isFirstEmitted = true
        firstValue = it
        if (isSecondEmitted) {
            mediatorLiveData.value = Pair(firstValue!!, secondValue!!)
            isFirstEmitted = false
            isSecondEmitted = false
        }
    }
    mediatorLiveData.addSource(second) {
        isSecondEmitted = true
        secondValue = it
        if (isFirstEmitted) {
            mediatorLiveData.value = Pair(firstValue!!, secondValue!!)
            isFirstEmitted = false
            isSecondEmitted = false
        }
    }

    return mediatorLiveData
}