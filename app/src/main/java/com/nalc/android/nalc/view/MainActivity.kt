package com.nalc.android.nalc.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_UP
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
                            .setTitle("로그인 하시겠습니까?")
                            .setMessage("채팅을 입력하려면 로그인해야합니다.")
                            .setPositiveButton("네") { _, _ ->

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