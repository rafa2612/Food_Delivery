package com.internshala.food_delivery.food_delivery.fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.*
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

import com.internshala.food_delivery.R
import com.internshala.food_delivery.food_delivery.adapter.couponrecycler
import com.internshala.food_delivery.food_delivery.adapter.recyclerallrest
import com.internshala.food_delivery.food_delivery.adapter.recyclertoprest

import com.internshala.food_delivery.food_delivery.model.resttop
import com.internshala.food_delivery.food_delivery.util.connection
import kotlinx.android.synthetic.main.fragment_home.*
import org.json.JSONException
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/**
 * A simple [Fragment] subclass.
 */
class home : Fragment() {

//    coupon

    lateinit var recyclercoupon: RecyclerView
    lateinit var couponlayoutmanager: RecyclerView.LayoutManager
    lateinit var couponimglist:ArrayList<Bitmap>

//     toprestaurenthorizontal
    lateinit var recyclerest:RecyclerView
    lateinit var restlayoutmanager:RecyclerView.LayoutManager
    lateinit var restlist1:ArrayList<resttop>
    lateinit var restlist2:ArrayList<resttop>
    lateinit var restrecycleradapter:recyclertoprest


//    all restaurent

    lateinit var recyclerallrest: RecyclerView
    lateinit var allrestlayoutmanager: RecyclerView.LayoutManager
    lateinit var allrestlist:ArrayList<resttop>
    lateinit var allrestrecycleradapter:recyclerallrest


//    sort
    lateinit var byname:TextView
    lateinit var byprice:TextView
    lateinit var byrating:TextView



    var ratingComparator = Comparator<resttop> { rest1, rest2 ->

        if (rest1.restrating.compareTo(rest2.restrating, true) == 0) {
            rest1.restrating.compareTo(rest2.restrating, true)
        } else {
            rest1.restrating.compareTo(rest2.restrating, true)
        }
    }



    var costComparator = Comparator<resttop> { rest1, rest2 ->

        rest1.restcostfor1.compareTo(rest2.restcostfor1, true)
    }

    var nameComparator = Comparator<resttop> { rest1, rest2 ->

        rest1.restname.compareTo(rest2.restname, true)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        val view=inflater.inflate(R.layout.fragment_home,container, false)

        recyclercoupon=view.findViewById(R.id.recyclercoupon)
        couponlayoutmanager= LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
        recyclerest=view.findViewById(R.id.recyclertoprestaurents)
        restlayoutmanager=LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
        recyclerallrest=view.findViewById(R.id.recyclerallrestaurents)
        allrestlayoutmanager=LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)



        var snapHelpers:SnapHelper=PagerSnapHelper()
        snapHelpers.attachToRecyclerView(recyclercoupon)
        var snapHelper:SnapHelper=PagerSnapHelper()
        snapHelper.attachToRecyclerView(recyclerest)


        setHasOptionsMenu(true)

        fetchcoupon()

        fetchtoprest()



        byname=view.findViewById(R.id.sortbyname)
        byrating=view.findViewById(R.id.sortbyrating)
        byprice=view.findViewById(R.id.sortbycost)


        byprice.setOnClickListener {
            Collections.sort(allrestlist, costComparator)

            allrestrecycleradapter.notifyDataSetChanged()
        }

        byrating.setOnClickListener {
            Collections.sort(allrestlist, ratingComparator)
            allrestlist.reverse()
            allrestrecycleradapter.notifyDataSetChanged()
        }


        byname.setOnClickListener {
            Collections.sort(allrestlist, nameComparator)

            allrestrecycleradapter.notifyDataSetChanged()
        }

















        return view
    }



    fun fetchcoupon(){

        var couponimages= intArrayOf(R.drawable.c1,R.drawable.c2,R.drawable.c3)


        couponimglist= ArrayList()

        for (i in 0 until couponimages.size){

            couponimglist.add(BitmapFactory.decodeResource(resources,couponimages[i]))
        }

        var couponadapter= couponrecycler(activity as Context,couponimglist)

        recyclercoupon.adapter=couponadapter
        recyclercoupon.layoutManager=couponlayoutmanager

    }



    fun fetchtoprest(){
        var i=0

        val queue= Volley.newRequestQueue(activity as Context)

        val url="http://13.235.250.119/v2/restaurants/fetch_result/"

        if (connection().checkconnectivity(activity as Context)) {

            val jsonObjectRequest = object : JsonObjectRequest(
                Method.GET, url, null,
                Response.Listener {

                    print("response is $it")

                    try {
//                        progresslayout.visibility=View.GONE
                        val response = it.getJSONObject("data")
                        val success = response.getBoolean("success")

                        if (success) {
                            restlist1 = ArrayList()
                            restlist2 = ArrayList()
                            allrestlist=ArrayList()
                            val data = response.getJSONArray("data")

                            while (i < data.length()-1) {

                                val restjsonobject1 = data.getJSONObject(i)
                                val restobject1 = resttop(
                                    restjsonobject1.getString("id"),
                                    restjsonobject1.getString("name"),
                                    restjsonobject1.getString("rating"),
                                    restjsonobject1.getString("cost_for_one"),
                                    restjsonobject1.getString("image_url")

                                )

                                i=i+1

                                val restjsonobject2 = data.getJSONObject(i)
                                val restobject2 = resttop(
                                    restjsonobject2.getString("id"),
                                    restjsonobject2.getString("name"),
                                    restjsonobject2.getString("rating"),
                                    restjsonobject2.getString("cost_for_one"),
                                    restjsonobject2.getString("image_url")
                                )
                                i=i+1



                                restlist1.add(restobject1)
                                restlist2.add(restobject2)
                                allrestlist.add(restobject1)
                                allrestlist.add(restobject2)
                                restrecycleradapter =
                                    recyclertoprest(activity as Context, restlist1,restlist2)

                                recyclerest.adapter = restrecycleradapter
                                recyclerest.layoutManager = restlayoutmanager




                            }

                            Collections.sort(restlist1, costComparator)
                            restlist1.reverse()
                            Collections.sort(restlist2, costComparator)
                            restlist2.reverse()
                            restrecycleradapter.notifyDataSetChanged()

                            allrestrecycleradapter=recyclerallrest(activity as Context,allrestlist)
                            recyclerallrest.adapter=allrestrecycleradapter
                            recyclerallrest.layoutManager=allrestlayoutmanager








                        } else {
                            Toast.makeText(activity as Context, "error", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: JSONException) {
                        Toast.makeText(
                            activity as Context,
                            e.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                Response.ErrorListener {
                    print("response is $it")
                    Toast.makeText(
                        activity as Context,
                        it.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            ){
                override fun getHeaders(): MutableMap<String, String> {

                    val headers=HashMap<String, String>()
                    headers["Content-type"]="application/json"
                    headers["token"]="26c5144c5b9c13"
//                    headers["Token"]="  e07c22a352bb4d"
                    return headers
                }


            }
            queue.add(jsonObjectRequest)
        }



        else
        {
            val dialog= AlertDialog.Builder(activity as Context)
            dialog.setTitle("Error")
            dialog.setMessage("Internet Connection not found")
            dialog.setPositiveButton("settings"){text,listener->
                val settingsintent= Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingsintent)
                activity?.finish()
            }
            dialog.setNegativeButton("cancel"){text, listener->

                ActivityCompat.finishAffinity(activity as Activity)
            }
            dialog.create()
            dialog.show()
        }

    }

}
