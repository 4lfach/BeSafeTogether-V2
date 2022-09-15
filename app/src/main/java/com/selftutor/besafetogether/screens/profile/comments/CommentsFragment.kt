package com.selftutor.besafetogether.screens.profile.comments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.selftutor.besafetogether.databinding.FragmentCommentsBinding

class CommentsFragment: Fragment() {

	private lateinit var binding: FragmentCommentsBinding

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		binding = FragmentCommentsBinding.inflate(inflater, container, false)

		return binding.root
	}
}