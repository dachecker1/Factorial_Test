package com.sumin.factorialtest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import java.math.BigInteger

class MainViewModel : ViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State>
        get() = _state

    fun calculate(value: String) {
        _state.value = Progress
        if (value.isNullOrBlank()) {
            _state.value = Error
            return
        }

        val number = value.toLong()
        viewModelScope.launch(CoroutineName("Factorial(number.toString()")) {
            val result = factorial(number)
            _state.value = Factorial(result)
        }
    }

    private suspend fun factorial(number: Long) : String{
       return withContext(Dispatchers.Default) {
            var result = BigInteger.ONE
            for (i in 1..number){
                result = result.multiply(BigInteger.valueOf(i))
            }
            result.toString()
        }
    }
}