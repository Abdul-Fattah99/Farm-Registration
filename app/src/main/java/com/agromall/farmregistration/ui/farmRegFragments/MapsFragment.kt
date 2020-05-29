package com.agromall.farmregistration.ui.farmRegFragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.agromall.farmregistration.R
import com.agromall.farmregistration.data.Farmer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.fragment_map.*

/**
 * A simple [Fragment] subclass.
 */
class MapsFragment(private val farmer: Farmer) : DialogFragment(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        farm_location_map.setText(farmer.name)

        map_view.onCreate(savedInstanceState)
        map_view.onResume()

        map_view.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap?) {

        map?.let {
            googleMap = it
        }

        farm_location_map.setText(farmer.name)
        // Add a marker in Sydney and move the camera
        val latitude = 6.535619
        val longitude = 3.384119
        val zoomLevel = 16f
        val overLaySize = 100f
        val homeLatLng =  LatLng(latitude,longitude)
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(homeLatLng, zoomLevel))
        //map?.addMarker(MarkerOptions().position(homeLatLng))

        val androidOverlay = GroundOverlayOptions()
            .image(BitmapDescriptorFactory.fromResource(R.drawable.polygon_map))
            .position(homeLatLng, overLaySize)

        map?.addGroundOverlay(androidOverlay)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }


}



