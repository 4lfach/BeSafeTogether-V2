package com.selftutor.besafetogether.screens.map

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import com.selftutor.besafetogether.R
import com.selftutor.besafetogether.databinding.FragmentMapBinding

class MapFragment : Fragment(R.layout.fragment_map) {

	companion object {
		fun newInstance() = MapFragment()
	}

	private lateinit var viewModel: MapViewModel
	private lateinit var binding: FragmentMapBinding

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
	}

	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)
		viewModel = ViewModelProvider(this)[MapViewModel::class.java]
		// TODO: Use the ViewModel
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		binding = FragmentMapBinding.inflate(inflater, container, false)
		return binding.root
	}
}