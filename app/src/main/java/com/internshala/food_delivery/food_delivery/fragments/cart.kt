package com.internshala.food_delivery.food_delivery.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.internshala.food_delivery.R
import kotlinx.android.synthetic.main.activity_restmenu.*
import kotlinx.android.synthetic.main.activity_restmenu.view.*


/**
 * A simple [Fragment] subclass.
 */
class cart : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.activity_cartactivity,container, false)







//        val x:String?=getArguments()?.getString("itemname")



        return view

    }




}
