package com.nalc.android.nalc.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import com.nalc.android.nalc.FirestoreCollection
import com.nalc.android.nalc.R
import com.nalc.android.nalc.databinding.ActivitySplashBinding
import com.nalc.android.nalc.model.UserModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)

        Firebase.initialize(this)

        lifecycleScope.launch {
            delay(1000)
            loadUserData()
        }
    }

    private fun loadUserData() {
        Firebase.auth.currentUser?.uid?.let {
            Firebase.firestore.collection(FirestoreCollection.USER.collectionName)
                .document(it).get()
                .addOnSuccessListener { user ->
                    val userModel = user.toObject(UserModel::class.java).apply { this?.uid = user.id}
                    goToMainActivity(userModel)
                }
                .addOnFailureListener {
                    goToMainActivity()
                }
        } ?: run {
            goToMainActivity()
        }
    }

    private fun goToMainActivity(user: UserModel? = null) {
        startActivity(Intent(this, MainActivity::class.java)
            .putExtra("user", user))
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        finish()
    }
}