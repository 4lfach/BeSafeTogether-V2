package com.selftutor.besafetogether.screens.profile.login

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.selftutor.besafetogether.R
import com.selftutor.besafetogether.databinding.FragmentLoginBinding
import com.selftutor.besafetogether.screens.BaseFragment
import com.selftutor.besafetogether.screens.factory
import com.selftutor.besafetogether.utils.Status

class LoginFragment: BaseFragment() {

	private lateinit var binding: FragmentLoginBinding
	private val viewModel : LoginViewModel by viewModels { factory() }

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		binding = FragmentLoginBinding.inflate(inflater, container, false)

		binding.createAccountTextView.setOnClickListener {
			findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
		}

		setLoginButton()
		setFieldsObserver()

		return binding.root
	}

	private fun setLoginButton(){
		with(binding){
			loginButton.setOnClickListener {
				viewModel.onLogin().observe(viewLifecycleOwner){
					when(it.status){
						Status.SUCCESS -> {
							val user = it.data
							val bundle = bundleOf()
							bundle.putSerializable(ARG_USER, user)
							findNavController().navigate(R.id.action_loginFragment_to_profileFragment, bundle)
						}
						Status.ERROR -> {
							showToast(R.string.unable_to_login)
							Log.d("LoginViewModel", it.data?.error ?: "smth wrong")
							progressBar.visibility = View.GONE
							loginButton.isEnabled = true
						}
						Status.LOADING -> {
							progressBar.visibility = View.VISIBLE
							loginButton.isEnabled = false
						}
					}
				}
			}
		}
	}

	private fun setFieldsObserver(){
		viewModel.email.observe(viewLifecycleOwner){
			if(it.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(it).matches()){
				binding.emailTextView.error = getString(R.string.email_field_empty)
				binding.emailTextView.error = getString(R.string.email_invalid)
				viewModel.canLogin = false
				return@observe
			}
			viewModel.canLogin = true
		}
		viewModel.password.observe(viewLifecycleOwner){
			if(it.isEmpty()){
				binding.passwordEditText.error = getString(R.string.password_field_empty)

				viewModel.canLogin = false
				return@observe
			}
			viewModel.canLogin = true
		}
	}

	companion object{
		private const val ARG_USER = "user"
	}
}