package com.nalc.android.nalc.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.nalc.android.nalc.FirestoreCollection
import com.nalc.android.nalc.model.SignUpUserModel
import com.nalc.android.nalc.model.UserModel
import java.util.*

class SignUpViewModel(uid: String) : ViewModel() {
    private val mSignUpUser = MutableLiveData<SignUpUserModel>().apply {
        value = SignUpUserModel(uid)
    }
    val signUpUser: LiveData<SignUpUserModel> = mSignUpUser

    private val mCreatedUserModel = MutableLiveData<UserModel>()
    val createdUserModel: LiveData<UserModel> = mCreatedUserModel

    fun registerMember() {
        val uuid = UUID.randomUUID()
        val storageRef = Firebase.storage.reference.child("profile/$uuid.jpg")

        storageRef.putFile(mSignUpUser.value!!.profile!!).addOnSuccessListener {
            val userModel = mSignUpUser.value!!.toUserModel(storageRef.name)
            Firebase.firestore.collection(FirestoreCollection.USER.collectionName)
                .document(userModel.uid)
                .set(userModel)
                .addOnSuccessListener {
                    mCreatedUserModel.value = userModel
                }
        }
    }
}
