package com.internshala.food_delivery.food_delivery.adapter

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.internshala.food_delivery.R

class couponrecycler(val context: Context,
                     val itemlist: ArrayList<Bitmap>): RecyclerView.Adapter<couponrecycler.couponviewholder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): couponrecycler.couponviewholder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.coupon_single_row,parent,false)
        return couponviewholder(view)
    }

    override fun getItemCount(): Int {
        return itemlist.size
    }

    override fun onBindViewHolder(holder: couponrecycler.couponviewholder, position: Int) {
        holder?.couponimg?.setImageBitmap(itemlist[position])
    }

    class couponviewholder(view: View):RecyclerView.ViewHolder(view){

        val couponimg: ImageView =view.findViewById(R.id.couponimage)
    }

}