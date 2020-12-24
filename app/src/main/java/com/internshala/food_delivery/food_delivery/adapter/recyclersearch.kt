package com.internshala.food_delivery.food_delivery.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.internshala.food_delivery.R
import com.internshala.food_delivery.food_delivery.activity.restmenuactivity
import com.internshala.food_delivery.food_delivery.model.resttop
import com.squareup.picasso.Picasso

class recyclersearch(val context: Context, val itemlist:ArrayList<resttop>) : RecyclerView.Adapter<recyclersearch.recyclersearchviewholder>(){

    class recyclersearchviewholder(view: View): RecyclerView.ViewHolder(view){

        val ll: LinearLayout =view.findViewById(R.id.recyclersearchsinglerow)
        val searchrestname: TextView =view.findViewById(R.id.searchallrestname)
        val searchrestrating: TextView =view.findViewById(R.id.searchallrestrating)
        val searchrestcostfor1: TextView =view.findViewById(R.id.searchallrestcostfor1)
        val searchrestimg: ImageView =view.findViewById(R.id.searchallrestimg)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): recyclersearchviewholder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.search_single_row,parent,false)
        return recyclersearch.recyclersearchviewholder(view)
    }

    override fun getItemCount(): Int {
        return itemlist.count()
    }

    override fun onBindViewHolder(holder: recyclersearchviewholder, position: Int) {
        val rest1:resttop=itemlist[position]
        val searchrestid:String=rest1.restid
        holder.searchrestname.text=rest1.restname
        holder.searchrestrating.text=rest1.restrating
        holder.searchrestcostfor1.text="Rs. "+rest1.restcostfor1+"/person"
        Picasso.get().load(rest1.restimgurl).error(R.drawable.radhika).into(holder.searchrestimg);


        holder.ll.setOnClickListener()
        {
            val intent = Intent(context, restmenuactivity::class.java)
            intent.putExtra("restaurantId", searchrestid.toString())
            intent.putExtra("restaurantName", holder.searchrestname.text.toString())
            intent.putExtra("restaurantimage", rest1.restimgurl.toString())
            context.startActivity(intent)
        }
    }

}