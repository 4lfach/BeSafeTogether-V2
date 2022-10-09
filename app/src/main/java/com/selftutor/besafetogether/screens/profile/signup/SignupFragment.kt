package com.selftutor.besafetogether.screens.profile.signup

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.selftutor.besafetogether.R
import com.selftutor.besafetogether.databinding.FragmentSignupBinding
import com.selftutor.besafetogether.screens.BaseFragment
import com.selftutor.besafetogether.screens.factory

class SignupFragment: BaseFragment(){

	private lateinit var binding: FragmentSignupBinding
	private val viewModel: SignupViewModel by viewModels { factory() }

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		binding = FragmentSignupBinding.inflate(inflater, container, false)

		binding.signupButton.setOnClickListener {
			if(viewModel.registerUser()){
				findNavController().navigate(R.id.action_signupFragment_to_profileFragment)
			}
			showToast(R.string.try_again_later)
		}

		binding.loginTextView.setOnClickListener {
			findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
		}

		setFieldsObserver()

		return binding.root
	}

	private fun setFieldsObserver() {
		viewModel.username.observe(viewLifecycleOwner){
			if(it.isEmpty()){
				binding.userNameTextView.error = getString(R.string.username_field_empty)
				viewModel.canRegister = false
				return@observe
			}
			viewModel.canRegister = true
		}
		viewModel.email.observe(viewLifecycleOwner){
			if(it.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(it).matches()){
				binding.emailTextView.error = getString(R.string.email_field_empty)
				binding.emailTextView.error = getString(R.string.email_invalid)
				viewModel.canRegister = false
				return@observe
			}
			viewModel.canRegister = true
		}
		viewModel.password.observe(viewLifecycleOwner){
			if(it.isEmpty() || viewModel.confirmPassword.value == it){
				binding.passwordEditText.error = getString(R.string.password_field_empty)
				binding.confirmPasswordEditText.error = getString(R.string.passwords_not_match)
				binding.passwordEditText.error = getString(R.string.passwords_not_match)

				viewModel.canRegister = false
				return@observe
			}
			viewModel.canRegister = true
		}
		viewModel.confirmPassword.observe(viewLifecycleOwner){
			if(viewModel.password.value == it){
				binding.confirmPasswordEditText.error = getString(R.string.passwords_not_match)
				binding.passwordEditText.error = getString(R.string.passwords_not_match)

				viewModel.canRegister = false
				return@observe
			}

			viewModel.canRegister = true
		}
	}

}
