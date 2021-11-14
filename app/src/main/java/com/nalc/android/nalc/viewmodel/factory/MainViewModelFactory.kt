package com.nalc.android.nalc.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nalc.android.nalc.model.UserModel
import com.nalc.android.nalc.viewmodel.MainViewModel

class MainViewModelFactory(private val user: UserModel?): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(user) as T
    }
}