package com.illicitintelligence.storagewithpermissions.network

import android.util.Log
import com.illicitintelligence.storagewithpermissions.model.Places
import com.illicitintelligence.storagewithpermissions.model.Result
import com.illicitintelligence.storagewithpermissions.util.Constants
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    var placeService: PlaceService

    init {
        placeService = createService(createRetrofit())
    }

    private fun createRetrofit(): Retrofit {

        return Retrofit
            .Builder()
            .baseUrl(Constants.PLACES_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun createService(retrofit: Retrofit): PlaceService {
        return retrofit.create(PlaceService::class.java)
    }

    fun getPlaces(latLong: String, radius: Int): Call<Places> {

        Log.d("TAG_X", "calling ${latLong}")
        return placeService.getRestaurantsNearMe(
            latLong,
            radius,
            "restaurant",
            "pizza",
            Constants.API_KEY
        )
    }
}