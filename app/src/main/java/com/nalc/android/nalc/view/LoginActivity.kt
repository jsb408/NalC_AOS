package com.nalc.android.nalc.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.databinding.DataBindingUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.nalc.android.nalc.FirestoreCollection
import com.nalc.android.nalc.R
import com.nalc.android.nalc.databinding.ActivityLoginBinding
import com.nalc.android.nalc.model.UserModel

class LoginActivity : AppCompatActivity() {
    private val binding: ActivityLoginBinding by lazy { DataBindingUtil.setContentView(this, R.layout.activity_login) }

    private lateinit var auth: FirebaseAuth

    private val registerGoogleLogin = registerForActivityResult(object: ActivityResultContract<Intent, Intent?>() {
        override fun createIntent(context: Context, input: Intent): Intent = input
        override fun parseResult(resultCode: Int, intent: Intent?): Intent? = intent
    }) { intent ->
        intent.let {
            val task = GoogleSignIn.getSignedInAccountFromIntent(it)

            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch(e: ApiException) {
                Log.e(MainActivity::class.simpleName, "Google sign in failed", e)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth

        setView()
    }

    private fun setView() {
        setButtons()
    }

    // region setView
    private fun setButtons() {
        binding.btnGoogleLogin.setOnClickListener {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

            val signInIntent = GoogleSignIn.getClient(this, gso).signInIntent

            registerGoogleLogin.launch(signInIntent)
        }
    }
    // endregion

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    login(task.result.user?.uid)
                } else {
                    Log.e(MainActivity::class.simpleName, "signInWithCredential: failure", task.exception)
                }
            }
    }

    private fun login(userUid: String?) {
        Firebase.firestore.collection(FirestoreCollection.USER.collectionName)
            .document(userUid ?: "").get().addOnSuccessListener {
                val userModel = it.toObject(UserModel::class.java)
                if (userModel == null) {
                    // TODO: 2021/11/14 SignUpActivity 연결
                } else {
                    intent.putExtra("user", userModel)
                    setResult(RESULT_OK, intent)
                    finish()
                }
            }.addOnFailureListener {
                Toast.makeText(this, it.localizedMessage, Toast.LENGTH_SHORT).show()
            }
    }
}