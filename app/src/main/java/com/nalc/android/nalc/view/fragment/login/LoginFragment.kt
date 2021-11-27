package com.nalc.android.nalc.view.fragment.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
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
import com.nalc.android.nalc.databinding.FragmentLoginBinding
import com.nalc.android.nalc.model.UserModel
import com.nalc.android.nalc.view.MainActivity
import com.nalc.android.nalc.viewmodel.LoginViewModel

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val loginViewModel by activityViewModels<LoginViewModel>()

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

    // region lifecycle
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        auth = Firebase.auth

        setView()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    // endregion

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

            val signInIntent = GoogleSignIn.getClient(requireActivity(), gso).signInIntent

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
                val userModel = it.toObject(UserModel::class.java).apply { this?.uid = it.id }
                if (userModel == null) {
                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment())
                } else {
                    loginViewModel.setUser(userModel)
                }
            }.addOnFailureListener {
                Toast.makeText(context, it.localizedMessage, Toast.LENGTH_SHORT).show()
            }
    }
}