package com.selftutor.besafetogether.screens.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.selftutor.besafetogether.R
import com.selftutor.besafetogether.databinding.FragmentProfileBinding
import com.selftutor.besafetogether.model.database.stopwords.StopWord
import com.selftutor.besafetogether.screens.BaseFragment
import com.selftutor.besafetogether.screens.factory
import com.selftutor.besafetogether.screens.profile.comments.CommentsFragment.Companion.ARG_USER_ID
import java.text.SimpleDateFormat
import java.util.*

class ProfileFragment : BaseFragment() {

	private val viewModel: ProfileViewModel by viewModels { factory() }

	private lateinit var binding: FragmentProfileBinding
	private lateinit var adapter: StopWordsAdapter

	//todo create User class with name, email, id
	private var userId: Int = 0

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {

		binding = FragmentProfileBinding.inflate(inflater, container, false)
		adapter = StopWordsAdapter(viewModel)

		viewModel.stopWords.observe(viewLifecycleOwner){
			it.let{
				adapter.updateList(it)
			}
		}

		viewModel.actionShowToast.observe(viewLifecycleOwner){
			showToast(it)
		}

		val layoutManager = LinearLayoutManager(requireContext())

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

			commentsButton.setOnClickListener {
				findNavController().navigate(R.id.action_profileFragment_to_commentsFragment, bundleOf(ARG_USER_ID to userId))
			}
		}

		return binding.root
	}
}