package com.selftutor.besafetogether.screens.map

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.selftutor.besafetogether.R
import com.selftutor.besafetogether.databinding.FragmentMapBinding

class MapFragment : Fragment(), OnMapReadyCallback {

	private lateinit var viewModel: MapViewModel
	private lateinit var binding: FragmentMapBinding

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		binding = FragmentMapBinding.inflate(inflater, container, false)

		return binding.root
	}

	override fun onMapReady(map: GoogleMap) {
		/*mMap = map
		if (checkPermissions()) {
			mMap.isMyLocationEnabled = true
			mMap.uiSettings.isMyLocationButtonEnabled = true
		} else {
			Toast.makeText(requireContext(), "Enable GPS to use this function", Toast.LENGTH_SHORT)
				.show()
		}

		mMap.setOnMarkerClickListener {marker->
			for (place in places){
				if(marker.position == place.latLng){
					showDangerPlaceSheetDialog(place.id, place.name)
				}
			}
			true
		}*/
	}
}