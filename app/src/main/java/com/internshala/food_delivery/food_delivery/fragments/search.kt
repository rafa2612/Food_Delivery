package com.internshala.food_delivery.food_delivery.fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.internshala.food_delivery.R
import com.internshala.food_delivery.food_delivery.adapter.recyclerallrest
import com.internshala.food_delivery.food_delivery.model.resttop
import com.internshala.food_delivery.food_delivery.util.connection
import org.json.JSONException

/**
 * A simple [Fragment] subclass.
 */
class search : Fragment() {

    lateinit var searchstring:EditText
    lateinit var recyclersearch: RecyclerView
    lateinit var searchlayoutmanager: RecyclerView.LayoutManager
//    lateinit var allrestlist:ArrayList<resttop>
    lateinit var searchrecycleradapter: recyclerallrest

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_search,container, false)


        recyclersearch=view.findViewById(R.id.recyclersearchrest)
        searchlayoutmanager= LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)

        searchstring=view.findViewById(R.id.searchstringg)






        fetchallrest()


        searchstring.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {

                // TODO Auto-generated method stub
                    // Here you may access the text from the object s, like s.toString()
            }

            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,

                after: Int
            ) {

                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable?) {

                // TODO Auto-generated method stub
//                Toast.makeText(context,"${s.toString()}",Toast.LENGTH_SHORT).show()
                if (s.toString().isEmpty()){
                    fetchallrest()
                }
                else{

                    fetchallreston(s.toString())

                }
            }
        })



        return view
    }


    fun fetchallrest(){

        var allrestlist:ArrayList<resttop>


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
                            allrestlist=ArrayList()
                            val data = response.getJSONArray("data")
                            if (searchstring.text.isEmpty()){
                            for (i in 0 until data.length()) {

                                val restjsonobject1 = data.getJSONObject(i)
                                val restobject1 = resttop(
                                    restjsonobject1.getString("id"),
                                    restjsonobject1.getString("name"),
                                    restjsonobject1.getString("rating"),
                                    restjsonobject1.getString("cost_for_one"),
                                    restjsonobject1.getString("image_url")

                                )

                                allrestlist.add(restobject1)
                                searchrecycleradapter=recyclerallrest(activity as Context,allrestlist)
                                recyclersearch.adapter=searchrecycleradapter
                                recyclersearch.layoutManager=searchlayoutmanager
                                searchrecycleradapter.notifyDataSetChanged()








                            }}












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



    fun fetchallreston(keyword:String){

        var allrestlist:ArrayList<resttop>


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
                            allrestlist=ArrayList()
                            val data = response.getJSONArray("data")

                                for (i in 0 until data.length()) {

                                    val restjsonobject1 = data.getJSONObject(i)
                                    val restobject1 = resttop(
                                        restjsonobject1.getString("id"),
                                        restjsonobject1.getString("name"),
                                        restjsonobject1.getString("rating"),
                                        restjsonobject1.getString("cost_for_one"),
                                        restjsonobject1.getString("image_url")

                                    )

                                    if (restobject1.restname.toLowerCase().contains(keyword.toLowerCase())){
                                    allrestlist.add(restobject1)
                                    searchrecycleradapter=recyclerallrest(activity as Context,allrestlist)
                                    recyclersearch.adapter=searchrecycleradapter
                                    recyclersearch.layoutManager=searchlayoutmanager
                                    searchrecycleradapter.notifyDataSetChanged()}








                                }












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
