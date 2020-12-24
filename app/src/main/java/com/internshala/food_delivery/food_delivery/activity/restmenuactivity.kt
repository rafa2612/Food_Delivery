package com.internshala.food_delivery.food_delivery.activity

import android.app.AlertDialog
import android.app.PendingIntent.getActivity
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.internshala.food_delivery.R
import com.internshala.food_delivery.R.layout.activity_restmenu
import com.internshala.food_delivery.food_delivery.adapter.recyclerrestmenu
import com.internshala.food_delivery.food_delivery.model.restmenu
import com.internshala.food_delivery.food_delivery.util.connection
import com.squareup.picasso.Picasso
import org.json.JSONException


class restmenuactivity : AppCompatActivity() {

    lateinit var restmenurecycler: RecyclerView
    lateinit var restmenulayoutmanager: RecyclerView.LayoutManager
    lateinit var restmenulist:ArrayList<restmenu>
    lateinit var restmenurecycleradapter:recyclerrestmenu
    lateinit var restmenuimg:ImageView
    lateinit var restmenuname:TextView




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_restmenu)

        supportActionBar?.title="Restaurant Menu"

        restmenuimg = findViewById(R.id.restmenuimage);
        restmenuname= findViewById(R.id.restmenuname)
        restmenurecycler=findViewById(R.id.recyclerrestmenu)
        restmenulayoutmanager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)


        var proceedtocart: Button =findViewById(R.id.proceedtocart)






        val dividerItemDecoration = DividerItemDecoration(
            restmenurecycler.getContext(),
            (restmenulayoutmanager as LinearLayoutManager).getOrientation()
        )
        restmenurecycler.addItemDecoration(dividerItemDecoration)


        var restid: String? = intent.getStringExtra("restaurantId")
        var restname: String? = intent.getStringExtra("restaurantName")
        var restimg: String? = intent.getStringExtra("restaurantimage")


        Picasso.get().load(restimg).into(restmenuimg);
        restmenuname.text=restname








        val queue = Volley.newRequestQueue(this)

        val url = "http://13.235.250.119/v2/restaurants/fetch_result/$restid"

        if (connection().checkconnectivity(this)) {

            val jsonObjectRequest = object : JsonObjectRequest(
                Method.GET, url, null,
                Response.Listener {

                    print("response is $it")

                    try {
//
                        val response = it.getJSONObject("data")
                        val success = response.getBoolean("success")

                        if (success) {
                            restmenulist = ArrayList()

                            val data = response.getJSONArray("data")

                            for (i in 0 until data.length()) {

                                val restjsonobject1 = data.getJSONObject(i)
                                val restobject = restmenu(
                                    restjsonobject1.getString("id"),
                                    restjsonobject1.getString("name"),
                                    restjsonobject1.getString("cost_for_one")
                                )




                                restmenulist.add(restobject)
                                restmenurecycleradapter =
                                    recyclerrestmenu(
                                        baseContext,
                                        restmenulist,
                                        restname!!,
                                        restid!!,
                                        proceedtocart

                                    )

                                restmenurecycler.adapter = restmenurecycleradapter
                                restmenurecycler.layoutManager = restmenulayoutmanager


                            }


                        } else {
                            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: JSONException) {
                        Toast.makeText(
                            this,
                            e.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                Response.ErrorListener {
                    print("response is $it")
                    Toast.makeText(
                        this,
                        it.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            ) {
                override fun getHeaders(): MutableMap<String, String> {

                    val headers = HashMap<String, String>()
                    headers["Content-type"] = "application/json"
                    headers["token"] = "26c5144c5b9c13"

                    return headers
                }


            }
            queue.add(jsonObjectRequest)


        }

else
{
    val dialog= AlertDialog.Builder(this)
    dialog.setTitle("Error")
    dialog.setMessage("Internet Connection not found")
    dialog.setPositiveButton("settings"){text,listener->
        val settingsintent= Intent(Settings.ACTION_WIRELESS_SETTINGS)
        startActivity(settingsintent)
        this?.finish()
    }
    dialog.setNegativeButton("cancel"){text, listener->

        ActivityCompat.finishAffinity(this)
    }
    dialog.create()
    dialog.show()
}

}

}
