package com.nalc.android.nalc.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_UP
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.nalc.android.nalc.R
import com.nalc.android.nalc.databinding.ActivityMainBinding
import com.nalc.android.nalc.model.UserModel
import com.nalc.android.nalc.viewmodel.MainViewModel
import com.nalc.android.nalc.viewmodel.factory.MainViewModelFactory

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { DataBindingUtil.setContentView(this, R.layout.activity_main) }
    private val viewModel by viewModels<MainViewModel> { MainViewModelFactory(intent.getSerializableExtra("user") as? UserModel)}

    private val registerLogin = registerForActivityResult(object : ActivityResultContract<Void, UserModel?>() {
        override fun createIntent(context: Context, input: Void): Intent = Intent(this@MainActivity, LoginActivity::class.java)
        override fun parseResult(resultCode: Int, intent: Intent?): UserModel? = intent?.getSerializableExtra("user") as? UserModel
    }) {
        it?.let { viewModel.setUser(it) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setView()
    }

    private fun setView() {
        setEditText()
    }

    // region setView
    @SuppressLint("ClickableViewAccessibility")
    private fun setEditText() {
        binding.etChatMain.setOnTouchListener { _, event ->
            when(event.action) {
                ACTION_UP -> {
                    if (viewModel.user.value == null)
                        AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Light_Dialog_Alert)
                            .setTitle("로그인이 필요합니다.")
                            .setMessage("댓글을 입력하시려면 로그인하셔야 합니다.\n로그인 화면으로 이동하시겠습니까?")
                            .setPositiveButton("네") { _, _ ->
                                registerLogin.launch()
                            }
                            .setNegativeButton("아니오") { _, _ -> }
                            .show()
                }
            }
            true
        }
    }
    // endregion
}