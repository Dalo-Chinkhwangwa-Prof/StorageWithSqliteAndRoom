package com.illicitintelligence.storagewithpermissions.network

import com.illicitintelligence.storagewithpermissions.model.Places
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceService {

    @GET("maps/api/place/nearbysearch/json")
    fun getRestaurantsNearMe(
        @Query("location") location: String,
        @Query("radius") radius: Int,
        @Query("type") type: String,
        @Query("keyword") keyword: String,
        @Query("key") apiKey: String): Call<Places>
//    AIzaSyDTip2-KoaN0x1BjWsbqMUO2nJKfNfpZbQ

//    https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=1500&type=restaurant&keyword=pizza&key=AIzaSyDTip2-KoaN0x1BjWsbqMUO2nJKfNfpZbQ
}