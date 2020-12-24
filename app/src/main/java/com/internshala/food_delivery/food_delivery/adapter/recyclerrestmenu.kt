package com.internshala.food_delivery.food_delivery.adapter

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton
import com.internshala.food_delivery.R
import com.internshala.food_delivery.food_delivery.activity.cartactivity
import com.internshala.food_delivery.food_delivery.model.restmenu


class recyclerrestmenu(val context: Context,val itemlist:ArrayList<restmenu>,val restname:String,val restid:String,val proceedtocart:Button): RecyclerView.Adapter<recyclerrestmenu.recyclerrestmenuviewholder>() {



    var itemSelectedCount: Int = 0
//    lateinit var proceedToCart: RelativeLayout
    var itemsSelectedId = arrayListOf<String>()
    var itemcount:Array<String?> = arrayOfNulls(10000)


//    var count=HashMap<String,Int>()



    class recyclerrestmenuviewholder(view: View):RecyclerView.ViewHolder(view){

        val itemname: TextView =view.findViewById(R.id.restmenuitem)
        val itemcost: TextView =view.findViewById(R.id.restmenucost)
        val itembuttom:ElegantNumberButton=view.findViewById(R.id.restmenubutton)






    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): recyclerrestmenu.recyclerrestmenuviewholder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.restmenu_single_row,parent,false)

        return recyclerrestmenuviewholder(view)



    }

    override fun getItemCount(): Int {
        return itemlist.count()
    }

    override fun onBindViewHolder(
        holder: recyclerrestmenu.recyclerrestmenuviewholder,
        position: Int
    ) {



        val rest: restmenu =itemlist[position]


        holder.itemname.text=rest.name
        holder.itemcost.text="Rs."+rest.cost_for_one
        holder.itembuttom.id=rest.id.toInt()






            holder.itembuttom.setOnClickListener(ElegantNumberButton.OnClickListener {
            var number:String=holder.itembuttom.number

                itemcount[holder.itembuttom.id]=number

                if (!itemsSelectedId.contains(holder.itembuttom.id.toString())){
                itemsSelectedId.add(holder.itembuttom.id.toString())}

                if ((itemcount[holder.itembuttom.id]=="0") and itemsSelectedId.contains(holder.itembuttom.id.toString())){
                    itemsSelectedId.remove(holder.itembuttom.id.toString())

                }


//                Toast.makeText(context,"${holder.itembuttom.id}",Toast.LENGTH_SHORT).show()



                })

//        Toast.makeText(context,"${restname}",Toast.LENGTH_SHORT).show()





        proceedtocart.setOnClickListener {

//            for (i in 0 until  itemlist.count()){
//
//                if (holder.itembuttom.number != "0")
//                    itemcount.add(holder.itembuttom.number.toString())
//            }

            val intent = Intent(context, cartactivity::class.java)
                intent.putExtra("restaurantId",itemsSelectedId)
                intent.putExtra("itemcountt",itemcount)
                intent.putExtra("restid",restid.toString())
                intent.putExtra("restname",restname.toString())
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)

        }




    }
}