package com.selftutor.besafetogether.screens.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.selftutor.besafetogether.databinding.FragmentProfileBinding
import com.selftutor.besafetogether.screens.factory

class ProfileFragment : Fragment() {

	private val viewModel: ProfileViewModel by viewModels { factory() }

	private lateinit var binding: FragmentProfileBinding
	private lateinit var adapter: StopWordsAdapter

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {

		binding = FragmentProfileBinding.inflate(inflater, container, false)
		adapter = StopWordsAdapter(viewModel)

		viewModel.stopWords.observe(viewLifecycleOwner){
			adapter.stopWords = it
		}

		viewModel.actionShowToast.observe(viewLifecycleOwner){
			showToast(it)
		}

		val layoutManager = LinearLayoutManager(requireContext())
		binding.stopWordRecyclerView.layoutManager = layoutManager
		binding.stopWordRecyclerView.adapter = adapter


		return binding.root
	}

	fun showToast(messageRes: Int){
		Toast.makeText(requireContext(), messageRes, Toast.LENGTH_SHORT).show()
	}
}