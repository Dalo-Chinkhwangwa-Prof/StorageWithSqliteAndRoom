package com.illicitintelligence.storagewithpermissions.database.room

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = arrayOf(LocationEntity::class), version = 1)
abstract class LocationDatabase: RoomDatabase() {
    abstract fun getDAO(): LocationDAO
}