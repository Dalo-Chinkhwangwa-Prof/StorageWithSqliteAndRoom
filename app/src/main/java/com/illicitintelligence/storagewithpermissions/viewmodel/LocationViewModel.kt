/*
package com.illicitintelligence.storagewithpermissions.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.illicitintelligence.storagewithpermissions.R
import com.illicitintelligence.storagewithpermissions.database.helper.LocationDatabasHelper

class LocationViewModel(application: Application): AndroidViewModel(application) {


    var databasHelper: LocationDatabasHelper
    var applicationContext: Context

    private val databaseLiveData: MutableLiveData<List<String>> = MutableLiveData()
    init {

        databasHelper = LocationDatabasHelper(application.applicationContext)
        applicationContext = application.applicationContext
    }




    fun getLocationData(): MutableLiveData<List<String>> {

        val cursor = databasHelper.getAllLocations()
        cursor.moveToPosition(-1)
        val list = mutableListOf<String>()
        while (cursor.moveToNext()){


            val locationString = applicationContext.getString(R.string.latitude_text, )


        }

        return databaseLiveData
    }



}*/
