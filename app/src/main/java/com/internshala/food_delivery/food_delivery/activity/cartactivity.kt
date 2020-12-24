package com.internshala.food_delivery.food_delivery.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.method.TextKeyListener.clear
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.internshala.food_delivery.R
import com.internshala.food_delivery.food_delivery.adapter.cartrecycler
import com.internshala.food_delivery.food_delivery.adapter.recyclertoprest
import com.internshala.food_delivery.food_delivery.model.cartitem
import com.internshala.food_delivery.food_delivery.model.resttop
import com.internshala.food_delivery.food_delivery.util.connection
import kotlinx.android.synthetic.main.activity_cartactivity.*
import kotlinx.android.synthetic.main.toprestaurent_single_row.*
import org.json.JSONException
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.math.cos
import kotlin.properties.Delegates

class cartactivity : AppCompatActivity() {

    lateinit var text: TextView
    lateinit var recyclercart: RecyclerView
    lateinit var cartlayoutmanager: RecyclerView.LayoutManager
    lateinit var cartrecycleradapter: cartrecycler
    lateinit var placeorder: Button
    lateinit var relcart:RelativeLayout


    var totalAmount: Int = 0
    var cartListItems = arrayListOf<cartitem>()
    var cost1: Int = 0

    val auth= FirebaseAuth.getInstance()
    val ref = FirebaseDatabase.getInstance().getReference("users")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cartactivity)

        var restnamee: ArrayList<String>? = intent.getStringArrayListExtra("restaurantId")
        var itemcountt: Array<String> = intent.getStringArrayExtra("itemcountt")
        var restaurantId:String? = intent.getStringExtra("restid")
        var restaurantName:String? = intent.getStringExtra("restname")
        var cost: Int

//
//        Toast.makeText(this,"${restaurantName}",Toast.LENGTH_SHORT).show()



        relcart=findViewById(R.id.relcart)
        relcart.visibility= View.VISIBLE
        recyclercart = findViewById(R.id.cartrecyclerview)
        val totalprice: TextView = findViewById(R.id.totalcartprice)
        cartlayoutmanager =
            LinearLayoutManager(this@cartactivity, LinearLayoutManager.VERTICAL, false)


        text = findViewById(R.id.carttext)
        placeorder = findViewById(R.id.placeorderbutton)
        val restnam:TextView=findViewById(R.id.restnamecart)
        val  adress:TextView=findViewById(R.id.adresscart)
        restnam.text=restaurantName.toString()


        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(p0: DataSnapshot) {



                for (y in p0.children) {
                    if (y.child("email").getValue()
                            .toString() == auth.currentUser?.email.toString()
                    ) {
//                        Toast.makeText(this@cartactivity,"${y.child("adress").getValue()}",Toast.LENGTH_SHORT).show()

                        adress.text=y.child("adress").getValue().toString()
                    }

                }
            }})


        placeorder.setOnClickListener(){
            val intent = Intent(this, payment::class.java)
            intent.putExtra("amount",totalAmount.toString())
            startActivity(intent)
        }




        var sortedList = restnamee?.sortedWith(compareBy({ it.toInt() }))

        val freq: MutableMap<String, Int>? = HashMap()

        if (restnamee != null) {
            for (s in restnamee) {
                var count = freq?.get(s)
                if (count == null) count = 0
                freq?.set(s, count + 1)

            }
        }














        if (connection().checkconnectivity(this)) {

//                cartProgressLayout.visibility = View.VISIBLE

            try {
                val queue = Volley.newRequestQueue(this)
                val url = "http://13.235.250.119/v2/restaurants/fetch_result/$restaurantId"


                val jsonObjectRequest = object : JsonObjectRequest(
                    Method.GET,
                    url,
                    null,
                    Response.Listener {

                        val response = it.getJSONObject("data")
                        val success = response.getBoolean("success")
                        if (success) {

                            val data = response.getJSONArray("data")
                            cartListItems.clear()
                            totalAmount = 0
                            cost = 0

                            for (i in 0 until data.length()) {

                                val cartItem = data.getJSONObject(i)
                                if (restnamee?.contains(cartItem.getString("id"))!!) {
                                    relcart.visibility=View.GONE
                                    val menuObject = cartitem(
                                        cartItem.getString("id"),
                                        cartItem.getString("name"),
                                        cartItem.getString("cost_for_one"),
                                        cartItem.getString("restaurant_id")
                                    )

                                    cost = cartItem.getString("cost_for_one").toString().toInt()


                                    cost =
                                        cost * itemcountt[cartItem.getString("id").toInt()].toInt()






                                    cost1 = cost
                                    totalAmount = totalAmount + cost


                                    cartListItems.add(menuObject)

                                }



                                cartrecycleradapter = cartrecycler(
                                    this,
                                    cartListItems,
                                    restaurantName!!,
                                    restaurantId!!,
                                    totalAmount,
                                    itemcountt
                                )
                                recyclercart.adapter = cartrecycleradapter
                                recyclercart.layoutManager = cartlayoutmanager
                            }

                            placeorder.text = "Place Order(Total: Rs. " + totalAmount + ")"
                            totalprice.text = "Rs."+totalAmount.toString()

                        }
//                            cartProgressLayout.visibility = View.INVISIBLE
                    },
                    Response.ErrorListener {

                        Toast.makeText(
                            this,
                            "Some Error occurred!!!",
                            Toast.LENGTH_SHORT
                        ).show()

//                            cartProgressLayout.visibility = View.INVISIBLE
                    }) {
                    override fun getHeaders(): MutableMap<String, String> {
                        val headers = HashMap<String, String>()
                        headers["Content-type"] = "application/json"
                        headers["token"] = "26c5144c5b9c13"
                        return headers
                    }
                }

                queue.add(jsonObjectRequest)

            } catch (e: JSONException) {
                Toast.makeText(
                    this,
                    "Some Unexpected error occurred!!!",
                    Toast.LENGTH_SHORT
                ).show()
            }

        } else {

            val alterDialog = androidx.appcompat.app.AlertDialog.Builder(this)
            alterDialog.setTitle("No Internet")
            alterDialog.setMessage("Check Internet Connection!")
            alterDialog.setPositiveButton("Open Settings") { _, _ ->
                val settingsIntent = Intent(Settings.ACTION_SETTINGS)
                startActivity(settingsIntent)
            }
            alterDialog.setNegativeButton("Exit") { _, _ ->
//                   finishAffinity()
            }
            alterDialog.setCancelable(false)
            alterDialog.create()
            alterDialog.show()
        }
    }



}


















