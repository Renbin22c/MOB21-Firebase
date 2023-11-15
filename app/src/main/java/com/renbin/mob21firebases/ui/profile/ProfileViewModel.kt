package com.renbin.mob21firebases.ui.profile

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.renbin.mob21firebases.core.service.AuthService
import com.renbin.mob21firebases.core.service.StorageService
import com.renbin.mob21firebases.data.model.User
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
class ProfileViewModel @Inject constructor(
    private val authService: AuthService,
    private val storageService: StorageService,
    private val userRepo: UserRepo
) : BaseViewModel() {
    private val _user = MutableStateFlow(User(name = "Unknown", email = "Unknown"))
    val user: StateFlow<User> = _user

    private val _finish: MutableSharedFlow<Unit> = MutableSharedFlow()
    val finish: SharedFlow<Unit> = _finish

    private val _profileUri = MutableStateFlow<Uri?>(null)
    val profileUri: StateFlow<Uri?> = _profileUri

    init {
        getCurrentUser()
        getProfilePicUri()
    }


    fun logout(){
        authService.logout()
        viewModelScope.launch(Dispatchers.IO) {
            _finish.emit(Unit)
        }
    }

    fun updateProfilePic(uri: Uri){
        user.value.id?.let {
            viewModelScope.launch(Dispatchers.IO) {
                val name = "$it.jpg"
                storageService.addImage(name, uri)
                getProfilePicUri()
            }
        }
    }

    private fun getProfilePicUri(){
        viewModelScope.launch(Dispatchers.IO) {
            authService.getCurrentUser()?.uid.let {
                _profileUri.value = storageService.getImage("$it.jpg")
            }
        }
    }

    private fun getCurrentUser() {
        val firebaseUser = authService.getCurrentUser()
        firebaseUser?.let {
            viewModelScope.launch(Dispatchers.IO) {
                safeApiCall { userRepo.getUser(it.uid) }?.let { user ->
                    _user.value = user
                }
            }
        }
    }

}