package com.nalc.android.nalc.model

import com.google.firebase.firestore.Exclude
import java.io.Serializable

data class UserModel(
    @get:Exclude
    var uid: String,

    val nickname: String,
    val sweat: Int,
    val temperature: Int,
    val profile: String
): Serializable