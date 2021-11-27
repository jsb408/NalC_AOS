package com.nalc.android.nalc.model

import android.net.Uri
import androidx.databinding.BaseObservable

data class SignUpUserModel(
    var nickname: String = "",
    var sweat: Int? = null,
    var temperature: Int? = null,
    var profile: Uri? = null
): BaseObservable() {
    val isValid get() = nickname.isNotEmpty() && sweat != null && temperature != null && profile != null

    fun setTemperature(temperature: Int) {
        this.temperature = temperature
        notifyChange()
    }

    fun setSweat(sweat: Int) {
        this.sweat = sweat
        notifyChange()
    }

    fun setProfileUri(uri: Uri) {
        this.profile = uri
        notifyChange()
    }
}
