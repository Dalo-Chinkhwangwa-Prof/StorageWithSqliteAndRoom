package com.illicitintelligence.storagewithpermissions.view

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.room.Room
import androidx.room.RoomDatabase
import com.illicitintelligence.storagewithpermissions.LocationBaseAdapter
import com.illicitintelligence.storagewithpermissions.R
import com.illicitintelligence.storagewithpermissions.database.helper.LocationDatabasHelper
import com.illicitintelligence.storagewithpermissions.database.room.LocationDatabase
import com.illicitintelligence.storagewithpermissions.database.room.LocationEntity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), LocationListener {


    lateinit var mySharedPreferences: SharedPreferences

    private var locationManager: LocationManager? = null

    private lateinit var databaseHelper: LocationDatabasHelper

    private lateinit var locationDatabase: LocationDatabase

    private val REQUEST_CODE = 707

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        locationDatabase = Room
            .databaseBuilder(
                this,
                LocationDatabase::class.java,
                "locationz.db"
            )
            .allowMainThreadQueries()
            .build()

        mySharedPreferences = getSharedPreferences(packageName, Context.MODE_PRIVATE)

//        databaseHelper =
//            LocationDatabasHelper(
//                this
//            )
        get_location_button.setOnClickListener { view ->

            location_textview.text =
                mySharedPreferences.getString("last_location", "No values") + " saved"
            setUpLocation()
        }

        open_settings.setOnClickListener { view ->

            val settingsIntent =
                Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", packageName, null)
            settingsIntent.data = uri
            startActivity(settingsIntent)
        }
    }

    private fun setUpLocation() {

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_DENIED
        ) {
//            Ask at runtime
            requestLocationPermission()


        } else {
//            TODO: set up location listener

            locationManager = (getSystemService(Context.LOCATION_SERVICE) as LocationManager)

            locationManager?.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                1000,
                1f,
                this
            )

        }
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_CONTACTS),
            REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CODE) {

            for (i in 0 until permissions.size) {

                if (permissions[i] == Manifest.permission.ACCESS_FINE_LOCATION) {

                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "${permissions[i]} was granted.", Toast.LENGTH_SHORT)
                            .show()
                        Log.d("TAG_X", "${permissions[i]} was granted.")
                    } else {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(
                                this,
                                permissions[i]
                            )
                        )
                            requestLocationPermission()
                        else
                            Log.d("TAG_X", "${permissions[i]} was denied indefinitely.")
                    }
                }
                if (permissions[i] == Manifest.permission.READ_CONTACTS) {

                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "${permissions[i]} was granted.", Toast.LENGTH_SHORT)
                            .show()
                        Log.d("TAG_X", "${permissions[i]} was granted.")
                    } else {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(
                                this,
                                permissions[i]
                            )
                        )
                            requestLocationPermission()
                        else
                            Log.d("TAG_X", "${permissions[i]} was denied indefinitely.")
                    }
                }
            }
        }
    }

    override fun onLocationChanged(location: Location) {

        val locationString = "Longtitude = ${location.longitude}, Latitude = ${location.latitude}"

        Log.d("TAG_X", locationString)
        Toast.makeText(this, locationString, Toast.LENGTH_SHORT).show()

        location_textview.text = locationString


        val editor = mySharedPreferences.edit()
        val previous =
            "${mySharedPreferences.getString("last_location", "No Value")}\n${locationString}"
        editor.putString("last_location", previous)
        editor.apply()

//        For SQLite
//        writeToDatabase(location)
//        readFromDatabase()

//        For Room
        writeToRoomDatabase(location)
        readFromRoomDatabase()

    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
    }

    override fun onProviderEnabled(provider: String?) {
    }

    override fun onProviderDisabled(provider: String?) {
    }

    override fun onDestroy() {
        super.onDestroy()
        if (locationManager != null) {
            locationManager?.removeUpdates(this)
        }
    }

    private fun showManualPermissionSet() {
        location_textview.text =
            "Location Permission is required for this application to work. Please go to " +
                    "settings and enable location permission for this app."

        get_location_button.visibility = View.GONE
        open_settings.visibility = View.VISIBLE
    }


    private fun writeToRoomDatabase(location: Location) {

        locationDatabase
            .getDAO()
            .insertLocation(
                LocationEntity(
                    location.latitude.toString(),
                    location.longitude.toString()
                )
            )

        readFromRoomDatabase()
    }

    private fun readFromRoomDatabase() {

        val listOfEntity = locationDatabase.getDAO()
            .getAllLocation()

        val baseAdapter = LocationBaseAdapter(listOfEntity)

        latlng_listview.adapter = baseAdapter
    }


    private fun writeToDatabase(location: Location) {

        databaseHelper.insertLocation(location)
        Toast.makeText(this, "Location saved!!", Toast.LENGTH_SHORT).show()
    }

    private fun readFromDatabase() {

        val cursor = databaseHelper.getAllLocations()
        cursor.moveToPosition(-1)

        val locationList = mutableListOf<String>()
        val idList = mutableListOf<Int>()

        Log.d("TAG_X", "From Database]]]]]]]]]]]]]")
        while (cursor.moveToNext()) {

            val lat = cursor.getString(cursor.getColumnIndex(LocationDatabasHelper.COLUMN_LATITUDE))
                .toDouble()
            val lng =
                cursor.getString(cursor.getColumnIndex(LocationDatabasHelper.COLUMN_LONGTITUDE))
                    .toDouble()

            val id = cursor.getInt(cursor.getColumnIndex(LocationDatabasHelper.COLUMN_LOCATION_ID))
            Log.d("TAG_X", "ID = $id")
            idList.add(id)
            locationList.add(getString(R.string.latitude_text, lat, lng))
        }

        latlng_listview.adapter = ArrayAdapter<String>(
            this,
            R.layout.location_item_layout,
            R.id.location_textview,
            locationList
        )
        latlng_listview.setOnItemClickListener { parent, view, position, id ->
            Log.d("TAG_X", "position $position ${idList[position]}")
            databaseHelper.deleteLocation(idList[position])
//            readFromDatabase()
        }
    }

}
