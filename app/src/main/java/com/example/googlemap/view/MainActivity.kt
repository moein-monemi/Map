package com.example.googlemap.view

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.googlemap.LOCATION_PERMISSION_REQUEST_CODE
import com.example.googlemap.PermissionUtils
import com.example.googlemap.PermissionUtils.PermissionDeniedDialog.Companion.newInstance
import com.example.googlemap.PermissionUtils.isPermissionGranted
import com.example.googlemap.R
import com.example.googlemap.api.RetrofitClient
import com.example.googlemap.base.BaseDialog
import com.example.googlemap.databinding.WeatherDialogBinding
import com.example.googlemap.model.Main
import com.example.googlemap.model.WeatherResult
import com.example.googlemap.util.ApiResponse
import com.example.googlemap.viewmodel.WeatherViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Error


class MainActivity : AppCompatActivity(), OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback {
    lateinit var map: GoogleMap
    lateinit var viewModel: WeatherViewModel
    private lateinit var weatherDialog: WeatherDialog
    private var permissionDenied = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
        val map: SupportMapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        map.getMapAsync(this)
        weatherDialog = WeatherDialog(this)

        viewModel.weatherResult.observe(this
        ) { value ->
            when (value) {
                is ApiResponse.Success -> {
                    showConfirmDialog(value.data.main)
                }
                is ApiResponse.Loading -> {
                    Toast.makeText(applicationContext, "در حال لود کردن...", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(applicationContext, "لطفا اینترنت خود را بررسی کنید!", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        googleMap.setOnMapClickListener {
            viewModel.getWeather(it.latitude.toString(), it.longitude.toString())
        }
        enableMyLocation()
    }


    private fun showConfirmDialog(main: Main?) {
        main?.let {
            weatherDialog.setParameter(main)
            weatherDialog.showDialog()
        }
    }



    // These code are for permissions
    private fun enableMyLocation() {

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            map.isMyLocationEnabled = true
            return
        }

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
            || ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)) {
            PermissionUtils.RationaleDialog.newInstance(
                LOCATION_PERMISSION_REQUEST_CODE, true
            ).show(supportFragmentManager, "dialog")
            return
        }

        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            super.onRequestPermissionsResult(
                requestCode,
                permissions,
                grantResults
            )
            return
        }

        if (isPermissionGranted(permissions, grantResults, android.Manifest.permission.ACCESS_FINE_LOCATION)
            || isPermissionGranted(permissions, grantResults, android.Manifest.permission.ACCESS_COARSE_LOCATION)) {
            enableMyLocation()
        } else {
            permissionDenied = true
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        if (permissionDenied) {
            showMissingPermissionError()
            permissionDenied = false
        }
    }

    private fun showMissingPermissionError() {
        newInstance(true).show(supportFragmentManager, "dialog")
    }
}


