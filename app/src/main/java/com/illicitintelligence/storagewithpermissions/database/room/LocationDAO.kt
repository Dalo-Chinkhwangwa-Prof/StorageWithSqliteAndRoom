package com.illicitintelligence.storagewithpermissions.database.room

import androidx.room.*

@Dao
interface LocationDAO {

    @Query("SELECT * FROM location")
    fun getAllLocation(): List<LocationEntity>

    @Delete
    fun deleteLocation(locationEntity: LocationEntity)

    @Insert
    fun insertLocation(vararg locationEntity: LocationEntity)

}