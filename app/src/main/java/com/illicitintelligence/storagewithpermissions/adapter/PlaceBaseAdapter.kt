package com.illicitintelligence.storagewithpermissions.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.Toast
import com.illicitintelligence.storagewithpermissions.R
import com.illicitintelligence.storagewithpermissions.database.room.LocationEntity
import com.illicitintelligence.storagewithpermissions.model.Result

class PlaceBaseAdapter(var placeList: List<Result>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view = LayoutInflater.from(parent?.context?.applicationContext)
            .inflate(R.layout.location_item_layout, parent, false)


        view.apply {

            placeList[position].let { result ->
                findViewById<TextView>(R.id.location_textview).text = result.name
            }
        }

        return view

    }

    override fun getItem(position: Int): Result = placeList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = placeList.size
}