package com.nalc.android.nalc.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nalc.android.nalc.model.UserModel

class MainViewModel(user: UserModel?) : ViewModel() {
    private val mUser = MutableLiveData<UserModel>().apply {
        user?.let { value = user }
    }
    val user: LiveData<UserModel> = mUser
}
