package com.renbin.mob21firebases.ui.login

import android.content.Intent
import android.content.IntentSender
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.renbin.mob21firebases.core.service.AuthService
import com.renbin.mob21firebases.data.repo.TodosRepo
import com.renbin.mob21firebases.data.repo.UserRepo
import com.renbin.mob21firebases.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authService: AuthService,
    private val userRepo: UserRepo,
    private val todosRepo: TodosRepo
): BaseViewModel() {

    private val _greet = MutableStateFlow("")
    val greet: StateFlow<String> = _greet

    init {
        viewModelScope.launch(Dispatchers.IO) {
            safeApiCall { todosRepo.greet()?.let {
                _greet.value = it
            } }
        }
    }

    fun login(email: String, pass: String){
        viewModelScope.launch(Dispatchers.IO) {
            val user = safeApiCall { authService.login(email, pass) }

            if(user == null){
                _error.emit("Email or Password is wrong")
            } else{
                _success.emit("Login successful")
            }
        }
    }
}