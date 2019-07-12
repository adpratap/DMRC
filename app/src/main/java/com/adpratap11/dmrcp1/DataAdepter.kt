package com.adpratap11.dmrcp1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class DataAdepter(val mCtx: Context, val layoutresid: Int, val List: List<model>) :
    ArrayAdapter<model>(mCtx, layoutresid, List) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutinflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutinflater.inflate(layoutresid, null)
        val textviewname = view.findViewById<TextView>(R.id.nameadmin)
        val textviewlocation = view.findViewById<TextView>(R.id.locationadmin)
        val textviewtime = view.findViewById<TextView>(R.id.timeadmin)
        val textviewremarks = view.findViewById<TextView>(R.id.remarksadmin)
        val textviewimage = view.findViewById<ImageView>(R.id.imageadmin)

        val list = List[position]
        textviewname.text = "Name : " + list.Name
        textviewlocation.text = "Location : " + list.Location
        textviewremarks.text = "Remarks : " + list.Remarks
        textviewtime.text = "Date : " + list.Timedate
        val uril = list.Image_url
        Glide.with(mCtx).load(uril).into(textviewimage)


        return view

    }
}