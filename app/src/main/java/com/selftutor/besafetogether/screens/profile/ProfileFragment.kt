package com.selftutor.besafetogether.screens.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.selftutor.besafetogether.R
import com.selftutor.besafetogether.databinding.FragmentProfileBinding
import com.selftutor.besafetogether.data.model.stopword.StopWord
import com.selftutor.besafetogether.screens.BaseFragment
import com.selftutor.besafetogether.screens.factory
import com.selftutor.besafetogether.utils.SessionManager
import java.text.SimpleDateFormat
import java.util.*

class ProfileFragment : BaseFragment() {

	private val viewModel: ProfileViewModel by viewModels { factory() }

	private lateinit var binding: FragmentProfileBinding
	private lateinit var adapter: StopWordsAdapter

	//todo create User class with name, email, id
	private var userId: String = ""

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		val token = SessionManager.getToken(requireContext())
		if (token.isNullOrBlank()) {
			navigate(R.id.action_profileFragment_to_loginFragment)
		}
	}
	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {

		binding = FragmentProfileBinding.inflate(inflater, container, false)
		adapter = StopWordsAdapter(viewModel)
		val layoutManager = LinearLayoutManager(requireContext())

		viewModel.stopWords.observe(viewLifecycleOwner){
			it.let{
				adapter.updateList(it)
			}
		}

		viewModel.actionShowToast.observe(viewLifecycleOwner){
			showToast(it)
		}


		with(binding){
			stopWordRecyclerView.layoutManager = layoutManager
			stopWordRecyclerView.adapter = adapter

			addWordButton.setOnClickListener {
				val word = binding.addWordEditText.text.toString()

				if(word.isNotBlank()){
					val sdf = SimpleDateFormat(getString(R.string.date_format))
					val currentDateAndTime: String = sdf.format(Date())
					val stopWord = StopWord(currentDateAndTime, word)

					viewModel.onStopWordAdd(stopWord)

					showToast(R.string.stop_word_added)
				}
			}

			contactsButton.setOnClickListener {
				findNavController().navigate(R.id.action_profileFragment_to_contactsFragment)
			}
		}

		return binding.root
	}
}