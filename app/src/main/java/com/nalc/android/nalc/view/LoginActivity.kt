package com.nalc.android.nalc.view

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.nalc.android.nalc.R
import com.nalc.android.nalc.databinding.ActivityLoginBinding
import com.nalc.android.nalc.viewmodel.LoginViewModel
import com.nalc.android.nalc.viewmodel.factory.LoginViewModelFactory

class LoginActivity : AppCompatActivity() {
    private val binding: ActivityLoginBinding by lazy { DataBindingUtil.setContentView(this, R.layout.activity_login) }
    private val viewModel by viewModels<LoginViewModel> { LoginViewModelFactory(this) }

    // region lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding

        bindViewModel()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
    // endregion

    private fun bindViewModel() {
        viewModel.user.observe(this) {
            intent.putExtra("user", it)
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}