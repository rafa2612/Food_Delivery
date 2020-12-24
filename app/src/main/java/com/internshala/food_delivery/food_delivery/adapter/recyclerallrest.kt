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

class recyclerallrest(val context: Context, val itemlist:ArrayList<resttop>) :RecyclerView.Adapter<recyclerallrest.recyclerallrestviewholder>(){

    class recyclerallrestviewholder(view: View): RecyclerView.ViewHolder(view){

        val ll:LinearLayout=view.findViewById(R.id.allrestlinear)
        val restname: TextView =view.findViewById(R.id.allrestname)
        val restrating: TextView =view.findViewById(R.id.allrestrating)
        val restcostfor1: TextView =view.findViewById(R.id.allrestcostfor1)
        val restimg: ImageView =view.findViewById(R.id.allrestimg)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): recyclerallrestviewholder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.allrest_single_row,parent,false)
        return recyclerallrest.recyclerallrestviewholder(view)
    }

    override fun getItemCount(): Int {
        return itemlist.count()
    }

    override fun onBindViewHolder(holder: recyclerallrestviewholder, position: Int) {
        val rest1:resttop=itemlist[position]
        val restid:String=rest1.restid
        holder.restname.text=rest1.restname
        holder.restrating.text=rest1.restrating
        holder.restcostfor1.text="Rs. "+rest1.restcostfor1+"/person"
        Picasso.get().load(rest1.restimgurl).error(R.drawable.radhika).into(holder.restimg);


        holder.ll.setOnClickListener()
        {
            val intent = Intent(context, restmenuactivity::class.java)
            intent.putExtra("restaurantId", restid.toString())
            intent.putExtra("restaurantName", holder.restname.text.toString())
            intent.putExtra("restaurantimage", rest1.restimgurl.toString())
            context.startActivity(intent)
        }
    }


}