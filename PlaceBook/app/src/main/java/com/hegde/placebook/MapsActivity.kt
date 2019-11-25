package com.hegde.placebook

import android.Manifest.*
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.GoogleApiClient.*
import com.google.android.gms.location.*
import com.google.android.gms.location.places.Places
//import com.google.android.libraries.places.api.Places
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PointOfInterest
import com.google.android.libraries.places.api.model.Place

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, OnConnectionFailedListener {

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    // private var locationRequest: LocationRequest? = null
    private lateinit var googleApiClient: GoogleApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        setupLocationClient()
    }

    companion object {
        private const val REQUEST_LOCATION = 1
        private const val TAG = "MapsActivity"
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        getCurrentLocation()

        // Add a marker in Sydney and move the camera
        //val sydney = LatLng(-34.0, 151.0)
        //mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        mMap.setOnPoiClickListener{
            Toast.makeText(this,it.name,Toast.LENGTH_LONG).show()
        }
    }

    private fun setupGoogleClient(){
        googleApiClient = Builder(this).enableAutoManage(this,this).addApi(Places.GEO_DATA_API).build()


    }

    private fun setupLocationClient() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
    }

    private fun requestLocationPermissions(){
        ActivityCompat.requestPermissions(this,
            arrayOf(permission.ACCESS_FINE_LOCATION),REQUEST_LOCATION)
    }

    private fun getCurrentLocation(){

        //1
        //First you check if the ACCESS_FINE_LOCATION permission has been granted before requesting a location
        if(ActivityCompat.checkSelfPermission(this, permission.ACCESS_FINE_LOCATION)!=
                PackageManager.PERMISSION_GRANTED){
            //2
            //if permission has not been granted method is called
            requestLocationPermissions()
        }else {

           /* if(locationRequest == null){

                locationRequest = LocationRequest.create()
                locationRequest?.let {locationRequest ->
                    // 1
                    //general guide to how accurate the locations should be.
                    locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                    //2 The desired interval in milliseconds to return updates
                    locationRequest.interval = 5000
                    //3
                    locationRequest.fastestInterval = 1000
                    //4
                    //new location ready.update the map on the new location
                    val locationCallback = object: LocationCallback() {
                        override fun onLocationResult(locationRequest: LocationResult?) {
                            getCurrentLocation()
                        }
                    }
                    //5
                    fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback,null)

                }
            }*/

            //3
            // lastlocation is a property and a Task that will run in the background to fetch the location
            // addOnCompleteListener notifies the user about the location and when the task is completed the default onComplete() method
            // is called with a Task<TResult> object.

            mMap.isMyLocationEnabled = true
            fusedLocationProviderClient.lastLocation.addOnCompleteListener{
                if(it.result!=null){ //it.result is a location object containing the last known location. It could be null if no location data available
                    //4
                    //simple object for storing latitude and longitude coordinate for a single map location
                    val latLng = LatLng(it.result!!.latitude, it.result!!.longitude)
                    //5
                    // addMarker() is used to create a marker at the location and display
               //     mMap.clear()
               //     mMap.addMarker(MarkerOptions().position(latLng).title("You are here!"))
                    //6
                    // CameraUpdate object is used to specify how the map camera is updated
                    val update = CameraUpdateFactory.newLatLngZoom(latLng,16.0f)
                    //7
                    mMap.moveCamera(update)
                }else {
                    //8
                    Log.e(TAG,"No location found")
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == REQUEST_LOCATION){
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getCurrentLocation()
            }else{
                Log.e(TAG,"Location permission denied")
            }
        }
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        Log.e(TAG, "Google play connection failed: " +     connectionResult.errorMessage)

    }

    private fun displayPoi(pointOfInterest: PointOfInterest){
        //1

    }
}

/**
   h4,Ra6,Rg6,Qh4
 */