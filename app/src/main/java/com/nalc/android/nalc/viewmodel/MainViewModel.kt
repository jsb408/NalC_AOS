package com.nalc.android.nalc.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nalc.android.nalc.model.UserModel

class MainViewModel(user: UserModel?) : ViewModel() {
    private val mUser = MutableLiveData<UserModel>().apply {
        user?.let { value = user }
    }
    val user: LiveData<UserModel> = mUser

    fun setUser(user: UserModel) {
        mUser.value = user
    }
}
