package com.illicitintelligence.storagewithpermissions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.Toast
import com.illicitintelligence.storagewithpermissions.database.room.LocationEntity

class LocationBaseAdapter(var locationList: List<LocationEntity>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view = LayoutInflater.from(parent?.context?.applicationContext)
            .inflate(R.layout.location_item_layout, parent, false)


        view.apply {

            findViewById<TextView>(R.id.location_textview).text = parent?.context?.getString(
                R.string.latitude_text,
                locationList[position].latitude.toDouble(),
                locationList[position].longtitude.toDouble()
            ) ?: "Error"

            setOnClickListener {
                Toast.makeText(
                    parent?.context,
                    "From base adaper : " + findViewById<TextView>(R.id.location_textview).text,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        return view

    }

    override fun getItem(position: Int): LocationEntity = locationList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = locationList.size
}