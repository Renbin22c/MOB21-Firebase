package com.renbin.mob21firebases.ui.register

import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.renbin.mob21firebases.core.service.AuthService
import com.renbin.mob21firebases.data.model.User
import com.renbin.mob21firebases.data.repo.UserRepo
import com.renbin.mob21firebases.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authService: AuthService,
    private val userRepo: UserRepo
): BaseViewModel() {

    fun register(email: String, pass: String, pass2: String){
        viewModelScope.launch(Dispatchers.IO) {
            val error = validate(email, pass, pass2)
            if(!error.isNullOrEmpty()){
                _error.emit(error.toString())
            } else{
                val user = safeApiCall { authService.register(email, pass) }
                if(user == null){
                    _error.emit("Cannot create user")
                } else{
                    userRepo.addUser(
                        user.uid,
                        User(name = "Renbin", email = user.email ?: "")
                    )
                    _success.emit("Register successful")
                }
            }
        }
    }

    private fun validate(email: String, pass: String, pass2: String):String? {
        return if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            "Please provide a valid email address"
        } else if(pass.length < 6){
            "Password length must be greater than 5"
        } else if(pass != pass2){
            "Password and Confirm Password not match"
        } else{
            null
        }
    }

//    private suspend fun validation(email: String, pass: String, pass2: String): Boolean {
//        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
//
//        if (!email.matches(emailRegex.toRegex())){
//            _error.emit("Email format is wrong")
//            return false
//        }
//        if(pass.length < 6){
//            _error.emit("Password Length less than 6")
//            return false
//        }
//        if (pass != pass2){
//            _error.emit("Password and Confirm Password not match")
//            return false
//        }
//        return true
//    }
}