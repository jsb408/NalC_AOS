package com.nalc.android.nalc.model

import java.io.Serializable

data class UserModel(
    var uid: String = "",
    val nickname: String = "",
    val sweat: Int = 0,
    val temperature: Int = 0,
    val profile: String = ""
): Serializable