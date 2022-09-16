package com.selftutor.besafetogether.screens.profile.login

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.selftutor.besafetogether.R
import com.selftutor.besafetogether.databinding.FragmentLoginBinding

class LoginFragment: Fragment() {

	private lateinit var binding: FragmentLoginBinding

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		binding = FragmentLoginBinding.inflate(inflater, container, false)

		binding.loginButton.setOnClickListener {
			if(checkFieldsAreFilled())
				findNavController().navigate(R.id.action_loginFragment_to_profileFragment)
		}

		binding.createAccountTextView.setOnClickListener {
			findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
		}

		return binding.root
	}

	private fun checkFieldsAreFilled(): Boolean {
		var counter = 0

		with(binding){
			val email = emailTextView.text.toString()
			val password = passwordEditText.text.toString()

			if(!Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.isNullOrEmpty()){
				emailTextView.error = "Email is invalid\n"
				counter++
			}
			if (password.isNullOrEmpty()){
				passwordEditText.error = "Password field is empty"
				counter++
			}

			if (counter != 0)
				return false
		}
		return true
	}
}