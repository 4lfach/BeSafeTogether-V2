package com.selftutor.besafetogether.screens.home

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import com.selftutor.besafetogether.R
import com.selftutor.besafetogether.databinding.FragmentHomeBinding
import com.selftutor.besafetogether.screens.BaseFragment
import com.selftutor.besafetogether.screens.factory
import com.selftutor.besafetogether.utils.FragmentPermission
import com.selftutor.besafetogether.utils.FragmentPermissionHelper

class HomeFragment: BaseFragment() {

	private val viewModel: HomeViewModel by viewModels { factory() }
	private lateinit var binding: FragmentHomeBinding

	private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
			permissions.entries.forEach {
				//Handles permission result
			}
		}
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		binding = FragmentHomeBinding.inflate(inflater, container, false)

		binding.gpsOnButton.setOnClickListener{
			val permissions = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
			permissionLauncher.launch(permissions)
		}

		return binding.root
	}

}