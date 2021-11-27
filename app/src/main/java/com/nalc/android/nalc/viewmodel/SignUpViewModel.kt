package com.nalc.android.nalc.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nalc.android.nalc.model.SignUpUserModel

class SignUpViewModel : ViewModel() {
    private val mSignUpUser = MutableLiveData<SignUpUserModel>().apply {
        value = SignUpUserModel()
    }
    val signUpUser: LiveData<SignUpUserModel> = mSignUpUser
}
