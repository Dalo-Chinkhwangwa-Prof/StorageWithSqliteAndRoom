package com.illicitintelligence.storagewithpermissions.database.helper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.location.Location

class LocationDatabasHelper(
    val context: Context
) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {
    companion object {
        val DATABASE_NAME = "location.db"
        val TABLE_NAME = "location"
        var DATABASE_VERSION = 1
        val COLUMN_LATITUDE = "latitude"
        val COLUMN_LONGTITUDE = "longtitude"
        val COLUMN_LOCATION_ID = "location_id"
    }

    override fun onCreate(db: SQLiteDatabase) {

        val createDatabase = "CREATE TABLE $TABLE_NAME ($COLUMN_LOCATION_ID INTEGER PRIMARY KEY, " +
                "$COLUMN_LATITUDE TEXT, " +
                "$COLUMN_LONGTITUDE TEXT)"

        db.execSQL(createDatabase)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val upgradeQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db.execSQL(upgradeQuery)
        onCreate(db)
    }

    fun getAllLocations(): Cursor {
        val db = readableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        return db.rawQuery(selectQuery, null)
    }

    fun insertLocation(location: Location) {
        val values = ContentValues()
        values.put(COLUMN_LATITUDE, location.latitude.toString())
        values.put(COLUMN_LONGTITUDE, location.longitude.toString())
        writableDatabase.insert(TABLE_NAME, null, values)
    }

    fun deleteLocation(id: Int) {
        val sqlDelete = "DELETE FROM $TABLE_NAME WHERE $COLUMN_LOCATION_ID = $id"
        writableDatabase.execSQL(sqlDelete)
    }

}