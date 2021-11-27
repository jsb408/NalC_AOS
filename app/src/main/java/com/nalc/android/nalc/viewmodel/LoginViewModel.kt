package com.nalc.android.nalc.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nalc.android.nalc.model.UserModel

class LoginViewModel: ViewModel() {
    private val mUser = MutableLiveData<UserModel>()
    val user: LiveData<UserModel> = mUser

    fun setUser(user: UserModel) {
        mUser.value = user
    }
}