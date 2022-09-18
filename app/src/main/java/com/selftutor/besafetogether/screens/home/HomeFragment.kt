package com.selftutor.besafetogether.screens.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.selftutor.besafetogether.databinding.FragmentHomeBinding
import com.selftutor.besafetogether.screens.factory

class HomeFragment: Fragment() {

	private val viewModel: HomeViewModel by viewModels { factory() }
	private lateinit var binding: FragmentHomeBinding

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		binding = FragmentHomeBinding.inflate(inflater, container, false)
		return binding.root
	}
}