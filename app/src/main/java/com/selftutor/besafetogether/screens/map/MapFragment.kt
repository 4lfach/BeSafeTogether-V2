package com.selftutor.besafetogether.screens.map

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.selftutor.besafetogether.R
import com.selftutor.besafetogether.databinding.DangerSpotSheetBinding
import com.selftutor.besafetogether.databinding.FragmentMapBinding
import com.selftutor.besafetogether.data.model.DangerPlace
import com.selftutor.besafetogether.screens.BaseFragment
import com.selftutor.besafetogether.screens.factory


class MapFragment : BaseFragment(), OnMapReadyCallback {

    private val viewModel: MapViewModel by viewModels { factory() }
    private lateinit var binding: FragmentMapBinding

    private lateinit var mMap: GoogleMap
    private var selectedLocation: LatLng = LatLng(51.154310, 71.433192)
    private var mapZoom: Float = 12.5f

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(inflater, container, false)

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        with(binding) {
            zoomInButton.setOnClickListener {
                mapZoom += 0.5f
                mMap.moveCamera(CameraUpdateFactory.zoomTo(mapZoom))
            }
            zoomOutButton.setOnClickListener {
                mapZoom -= 0.5f
                mMap.moveCamera(CameraUpdateFactory.zoomTo(mapZoom))
            }

        }

        return binding.root
    }

    override fun onMapReady(map: GoogleMap) {
        mMap = map
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(selectedLocation, 12.5f))

        if (!checkPermissions()) {
            showToast("GPS permission is not granted.")
            return
        }

        addDangerPlaceMarkers(viewModel.places.value!!)

        mMap.setOnMarkerClickListener { marker ->
            for (place in viewModel.places.value!!) {
                if(marker.position == place.latLng)
                    showDangerPlaceSheet(place)
            }
            true
        }
    }

    private fun addDangerPlaceMarkers(places: List<DangerPlace>) {
        for (place in places) {
            mMap.addMarker(
                MarkerOptions()
                    .title("Danger")
                    .position(place.latLng)
                    .icon(
                        BitmapDescriptorFactory.fromBitmap(
                            resizeMapIcons(
                                40,
                                40
                            )!!
                        )
                    )
            )
        }
    }

    private fun showDangerPlaceSheet(dangerPlace: DangerPlace) {
        val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.bottomSheetDialogTheme)
        val inflater = LayoutInflater.from(requireContext())

        val bsdBinding = DangerSpotSheetBinding.inflate(inflater)
        bottomSheetDialog.setContentView(bsdBinding.root)

        with(bsdBinding) {
            val adapter = DangerCommentsAdapter(dangerPlace.comments)

            commentsRecyclerView.adapter = adapter
            commentsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

            commentNumTextView.text = dangerPlace.comments.size.toString()
            averRatingTextView.text = dangerPlace.averageRating.toString()
            placeNameTextView.text = dangerPlace.address
        }

        bottomSheetDialog.show()
    }

    private fun resizeMapIcons(width: Int, height: Int): Bitmap? {
        val imageBitmap = BitmapFactory.decodeResource(
            resources,
            R.drawable.warning
        )
        return Bitmap.createScaledBitmap(imageBitmap, width, height, false)
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
            return true
        }
        return false
    }
}