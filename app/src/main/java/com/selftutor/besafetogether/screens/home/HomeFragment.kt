package com.selftutor.besafetogether.screens.home

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import com.selftutor.besafetogether.R
import com.selftutor.besafetogether.databinding.FragmentHomeBinding
import com.selftutor.besafetogether.screens.BaseFragment
import com.selftutor.besafetogether.screens.factory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


class HomeFragment: BaseFragment() {

	private val viewModel: HomeViewModel by viewModels { factory() }
	private lateinit var binding: FragmentHomeBinding

	private lateinit var locationManager: LocationManager

	private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

	private var buttonCounter = 0

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
			permissions.entries.forEach {
				//Handles permission result
			}
		}

		locationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		binding = FragmentHomeBinding.inflate(inflater, container, false)
		binding.viewModel = viewModel
		binding.lifecycleOwner = this

		val permissions = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
		permissionLauncher.launch(permissions)

		with(binding){

			gpsOnButton.setOnClickListener{
				if (!locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER)) {
					buildAlertMessageNoGps()
					return@setOnClickListener
				}

				viewModel?.onGpsPermissionGranted(true)
			}
			setContactsButton.setOnClickListener {
				navigate(R.id.action_homeFragment_to_contactsFragment)
			}
			setStopWordsButton.setOnClickListener {
				navigate(R.id.action_homeFragment_to_profileFragment)
			}
			startScanButton.setOnClickListener{
				startScanButton()
			}
		}

		return binding.root
	}

	private fun buildAlertMessageNoGps() {
		val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
		builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
			.setCancelable(false)
			.setPositiveButton("Yes") { _, _ -> startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) }
			.setNegativeButton("No") { dialog, _ -> dialog.cancel() }
		val alert: AlertDialog = builder.create()
		alert.show()
	}

	private fun checkGpsPermissions(): Boolean {
		if (ActivityCompat.checkSelfPermission(
				requireContext(),
				Manifest.permission.ACCESS_COARSE_LOCATION
			) == PackageManager.PERMISSION_GRANTED &&
			ActivityCompat.checkSelfPermission(
				requireContext(),
				Manifest.permission.ACCESS_FINE_LOCATION
			) == PackageManager.PERMISSION_GRANTED
		) {
			return true
		}
		return false
	}

	private fun startScanButton(){
		with(binding){
			if(buttonCounter % 2 == 0){
				viewModel?.onStartScan()
				startScanButton.text = resources.getString(R.string.scanning)
				requirementsCard.visibility = View.GONE
				welcomeTextView.visibility = View.GONE
				detailTextView.visibility = View.GONE
			} else{
				viewModel?.onStopScan()
				startScanButton.text = resources.getString(R.string.start_scanning)
				requirementsCard.visibility = View.VISIBLE
				requirementsCard.visibility = View.VISIBLE
				welcomeTextView.visibility = View.VISIBLE
				detailTextView.visibility = View.VISIBLE
			}
		}
		buttonCounter++
	}
}