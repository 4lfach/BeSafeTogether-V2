package com.selftutor.besafetogether.screens.profile.signup

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.selftutor.besafetogether.R
import com.selftutor.besafetogether.databinding.FragmentSignupBinding

class SignupFragment: Fragment() {

	private lateinit var binding: FragmentSignupBinding

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		binding = FragmentSignupBinding.inflate(inflater, container, false)

		binding.signupButton.setOnClickListener {
			if(checkUserCredentials()){
				findNavController().navigate(R.id.action_signupFragment_to_profileFragment)
			}
		}

		binding.loginTextView.setOnClickListener {
			findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
		}

		return binding.root
	}

	private fun checkUserCredentials(): Boolean {
		var counter = 0

		with(binding){
			val email = emailTextView.text.toString()
			val username = userNameTextView.text.toString()
			val phone = phoneTextView.text.toString()
			val password = passwordEditText.text.toString()
			val confirmPassword = confirmPasswordEditText.text.toString()

			if(!Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.isEmpty()){
				emailTextView.error = "Email is invalid\n"
				counter++
			}
			if(username.isEmpty()){
				userNameTextView.error = "Username field is empty"
				counter++
			}
			if(!phone.isEmpty()){
				userNameTextView.error = "Phone field is empty"
				counter++
			}
			if(!checkUsernameExists()){
				userNameTextView.error = "This username is already taken"
				counter++
			}
			if(!checkEmailExists()){
				emailTextView.error = "This email is already taken"
				counter++
			}
			if (password.isEmpty()){
				passwordEditText.error = "Password field is empty\n"
				counter++
			}
			if(password != confirmPassword){
				passwordEditText.error = "Passwords do not match"
				confirmPasswordEditText.error = "Passwords do not match"
			}
			if (counter != 0)
				return false
		}
		return true
	}

	private fun checkUsernameExists(): Boolean {
		//todo connect to api repository to check if username already exists. SignUpViewModel has to handle it
		return true
	}

	private fun checkEmailExists(): Boolean{
		//todo connect to api repository to check if email already exists. SignUpViewModel has to handle it
		return true
	}

}
