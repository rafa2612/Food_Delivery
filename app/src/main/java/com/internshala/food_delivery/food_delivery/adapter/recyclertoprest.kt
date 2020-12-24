package com.internshala.food_delivery.food_delivery.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.internshala.food_delivery.R
import com.internshala.food_delivery.food_delivery.activity.restmenuactivity
import com.internshala.food_delivery.food_delivery.model.resttop
import com.squareup.picasso.Picasso

class recyclertoprest(val context:Context,val itemlist1:ArrayList<resttop>,val itemlist2:ArrayList<resttop>):RecyclerView.Adapter<recyclertoprest.recyclertoprestviewholder>() {




    class recyclertoprestviewholder(view: View):RecyclerView.ViewHolder(view){

        val ll1:LinearLayout=view.findViewById(R.id.toprestlinear1)
        val ll2:LinearLayout=view.findViewById(R.id.toprestlinear2)

        val restname:TextView=view.findViewById(R.id.restname)
        val restrating:TextView=view.findViewById(R.id.restrating)
        val restcostfor1:TextView=view.findViewById(R.id.restcostfor1)
        val restimg: ImageView =view.findViewById(R.id.toprestimg)
        val restname1:TextView=view.findViewById(R.id.restname1)
        val restrating1:TextView=view.findViewById(R.id.restrating1)
        val restcostfor1_1:TextView=view.findViewById(R.id.restcostfor1_1)
        val restimg1: ImageView =view.findViewById(R.id.toprestimg1)

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): recyclertoprestviewholder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.toprestaurent_single_row,parent,false)
        return recyclertoprestviewholder(view)
    }



    override fun getItemCount(): Int {
        return itemlist1.count()
    }



    override fun onBindViewHolder(holder: recyclertoprestviewholder, position: Int) {

        val rest1:resttop=itemlist2[position]



        holder.restname1.text=rest1.restname
        holder.restrating1.text=rest1.restrating
        holder.restcostfor1_1.text="Rs. "+rest1.restcostfor1+"/person"
        val restid1:String=rest1.restid
        Picasso.get().load(rest1.restimgurl).error(R.drawable.radhika).into(holder.restimg1);

        val rest:resttop=itemlist1[position]

        val restid2:String=rest.restid

        holder.restname.text=rest.restname
        holder.restrating.text=rest.restrating
        holder.restcostfor1.text="Rs. "+rest.restcostfor1+"/person"
        Picasso.get().load(rest.restimgurl).error(R.drawable.radhika).into(holder.restimg);



        holder.ll1.setOnClickListener() {




            Toast.makeText(context, "${holder.restname.text.toString()}", Toast.LENGTH_SHORT).show()
            val intent = Intent(context, restmenuactivity::class.java)
            intent.putExtra("restaurantId", restid2.toString())
            intent.putExtra("restaurantName", holder.restname.text.toString())
            intent.putExtra("restaurantimage", rest.restimgurl.toString())

            context.startActivity(intent)
        }

        holder.ll2.setOnClickListener() {


            val intent = Intent(context, restmenuactivity::class.java)
            intent.putExtra("restaurantId", restid1.toString())
            intent.putExtra("restaurantName", holder.restname1.text.toString())
            intent.putExtra("restaurantimage", rest1.restimgurl.toString())
            context.startActivity(intent)
        }




    }


}