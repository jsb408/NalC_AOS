package com.nalc.android.nalc.model

import com.google.firebase.firestore.DocumentReference
import java.util.*

data class ReplyModel(
    val writer: DocumentReference? = null,
    val date: Date = Date(),
    val content: String = ""
)
