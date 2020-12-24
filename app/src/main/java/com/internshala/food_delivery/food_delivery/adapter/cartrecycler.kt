package com.internshala.food_delivery.food_delivery.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.internshala.food_delivery.R
import com.internshala.food_delivery.food_delivery.model.cartitem

class cartrecycler(val context: Context,val itemlist:ArrayList<cartitem>,val restname:String,val restid:String,val total:Int,var itemcount:Array<String>): RecyclerView.Adapter<cartrecycler.cartrecyclerviewholder>() {

    class cartrecyclerviewholder(view: View):RecyclerView.ViewHolder(view){

        val itemname: TextView =view.findViewById(R.id.cartitemname)
        val itemcost: TextView =view.findViewById(R.id.cartitemcost)






    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): cartrecycler.cartrecyclerviewholder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.cart_single_row,parent,false)

        return cartrecycler.cartrecyclerviewholder(view)
    }

    override fun getItemCount(): Int {
        return itemlist.count()
    }

    override fun onBindViewHolder(holder: cartrecyclerviewholder, position: Int) {

if (itemcount[itemlist[position].itemId.toInt()]!="0") {












    val rest: cartitem = itemlist[position]


    holder.itemname.text = rest.itemName + " * "
    holder.itemcost.text =
        "Rs." + ((itemcount[rest.itemId.toInt()].toInt()) * (rest.itemPrice.toInt())).toString()








    
    }
    }




}