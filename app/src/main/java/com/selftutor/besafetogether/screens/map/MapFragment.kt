package com.selftutor.besafetogether.screens.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.selftutor.besafetogether.R
import com.selftutor.besafetogether.databinding.FragmentMapBinding
import com.selftutor.besafetogether.screens.BaseFragment
import com.selftutor.besafetogether.screens.factory

class MapFragment : BaseFragment(), OnMapReadyCallback {

    private val viewModel: MapViewModel by viewModels { factory() }
    private lateinit var binding: FragmentMapBinding

    private lateinit var googleMap: GoogleMap
    private var mapReady: Boolean = false
    private var selectedLocation: LatLng = LatLng(51.154310, 71.433192)
    private var mapZoom: Float = 12.5f


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapBinding.inflate(inflater, container, false)

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        with(binding){
            zoomInButton.setOnClickListener {
                mapZoom += 0.5f
                googleMap.moveCamera( CameraUpdateFactory.zoomTo(mapZoom) )
            }
            zoomOutButon.setOnClickListener {
                mapZoom -= 0.5f
                googleMap.moveCamera( CameraUpdateFactory.zoomTo(mapZoom) )
            }
        }

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onStop() {
        super.onStop()

    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        googleMap.moveCamera( CameraUpdateFactory.newLatLngZoom(selectedLocation , 12.5f))

    }

    private fun addUserDangerMarkers(googleMap: GoogleMap) {

    }

    private fun showDangerBottomSheet() {

    }
}