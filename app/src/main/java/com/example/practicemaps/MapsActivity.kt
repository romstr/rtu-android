package com.example.practicemaps

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.GoogleMap
import com.google.android.libraries.maps.SupportMapFragment
import com.google.android.libraries.maps.model.BitmapDescriptorFactory
import com.google.android.libraries.maps.model.LatLng
import com.google.maps.android.ktx.addGroundOverlay
import com.google.maps.android.ktx.addMarker

class MapsActivity : AppCompatActivity() {

    private lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(::onMapReady)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.map_options, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.normal_map -> {
            map.mapType = GoogleMap.MAP_TYPE_NORMAL
            true
        }
        R.id.hybrid_map -> {
            map.mapType = GoogleMap.MAP_TYPE_HYBRID
            true
        }
        R.id.satellite_map -> {
            map.mapType = GoogleMap.MAP_TYPE_SATELLITE
            true
        }
        R.id.terrain_map -> {
            map.mapType = GoogleMap.MAP_TYPE_TERRAIN
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        if (requestCode == REQUEST_LOCATION_PERMISSION && PERMISSION_GRANTED in grantResults) {
            enableMyLocation()
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment.
     * This method will only be triggered once the user has installed Google Play services
     * and returned to the app.
     */
    private fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        setHome()

        setMapLongClick()

        setPoiClick()

        enableMyLocation()
    }

    private fun setHome() {
        val latitude = 37.422160
        val longitude = -122.084270
        val homeLatLng = LatLng(latitude, longitude)
        val zoomLevel = 15f

        map.addMarker { position(homeLatLng) }
        map.addGroundOverlay {
            image(bitmapDescriptorFromVector(R.drawable.ic_launcher_foreground))
            position(homeLatLng, OVERLAY_SIZE)
        }
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(homeLatLng, zoomLevel))
    }

    private fun setMapLongClick() {
        map.setOnMapLongClickListener { coordinates ->
            map.addMarker {
                position(coordinates)
                title(getString(R.string.dropped_pin))
                snippet("Lat: ${coordinates.latitude}, Long: ${coordinates.longitude}")
                icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
            }
        }
    }

    private fun setPoiClick() {
        map.setOnPoiClickListener { pointOfInterest ->
            map.addMarker {
                position(pointOfInterest.latLng)
                title(pointOfInterest.name)
            }.showInfoWindow()
        }
    }

    private fun enableMyLocation() {
        if (checkSelfPermission(this, ACCESS_FINE_LOCATION) == PERMISSION_GRANTED) {
            map.isMyLocationEnabled = true
        } else {
            requestPermissions(this, arrayOf(ACCESS_FINE_LOCATION), REQUEST_LOCATION_PERMISSION)
        }
    }

    private fun Context.bitmapDescriptorFromVector(vectorResId: Int) =
        ContextCompat.getDrawable(this, vectorResId)!!.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            val bitmap =
                Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }

    companion object {
        private const val OVERLAY_SIZE = 500f
        private const val REQUEST_LOCATION_PERMISSION = 1

    }
}