package com.illicitintelligence.storagewithpermissions.database.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location")
class LocationEntity(
    @PrimaryKey(autoGenerate = true)
    var locationId: Int?,

    @ColumnInfo(name = "latitude")
    var latitude: String,

    @ColumnInfo(name = "longtitude")
    var longtitude: String
){
    constructor(
         lat: String, lng: String
    )   : this(null, lat, lng)

}