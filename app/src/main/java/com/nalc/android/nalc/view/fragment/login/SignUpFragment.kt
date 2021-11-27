package com.nalc.android.nalc.view.fragment.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEachIndexed
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.nalc.android.nalc.databinding.FragmentSignUpBinding
import com.nalc.android.nalc.view.custom.BodyTypeButton
import com.nalc.android.nalc.viewmodel.SignUpViewModel

class SignUpFragment : Fragment() {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<SignUpViewModel>()

    private val registerPermission = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
        if (!it.containsValue(false)) {
            registerGetImage.launch("image/*")
        }
    }

    private val registerGetImage = registerForActivityResult(ActivityResultContracts.GetContent()) {
        it?.let { uri ->
            viewModel.signUpUser.value!!.setProfileUri(uri)
        }
    }

    // region lifecycle
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)

        setView()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    // endregion

    private fun setView() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setToolbar()
        setProfileImageButton()
        setBodyTypeButtons()
    }

    // region setView
    private fun setToolbar() {
        (activity as? AppCompatActivity)?.apply {
            setSupportActionBar(binding.toolbar)
            supportActionBar?.apply {
                setDisplayShowTitleEnabled(false)
                setDisplayHomeAsUpEnabled(true)
            }
        }
    }

    private fun setProfileImageButton() {
        binding.ivProfileSignUp.setOnClickListener {
            registerPermission.launch(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE))
        }
    }

    private fun setBodyTypeButtons() {
        setTemperatureButtons()
        setSweatButtons()
    }

    private fun setTemperatureButtons() {
        binding.llTemperatureSignUp.forEachIndexed { index, button ->
            (button as? BodyTypeButton)?.setOnClickListener {
                viewModel.signUpUser.value!!.setTemperature(index)
            }
        }
    }

    private fun setSweatButtons() {
        binding.llSweatSighUp.forEachIndexed { index, button ->
            (button as? BodyTypeButton)?.setOnClickListener {
                viewModel.signUpUser.value!!.setSweat(index)
            }
        }
    }
    // endregion
}