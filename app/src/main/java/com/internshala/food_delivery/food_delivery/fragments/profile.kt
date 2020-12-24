package com.internshala.food_delivery.food_delivery.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import com.internshala.food_delivery.R

/**
 * A simple [Fragment] subclass.
 */
class profile : Fragment() {

    lateinit var proname:EditText
    lateinit var proemail:EditText
    lateinit var promobile:EditText
    lateinit var progender:EditText
    lateinit var proadress:EditText
    lateinit var prosave:Button
    lateinit var auth:FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_profile,container, false)

        proname=view.findViewById(R.id.proname)
        proemail=view.findViewById(R.id.proemail)
        promobile=view.findViewById(R.id.promobile)
        progender=view.findViewById(R.id.progender)
        proadress=view.findViewById(R.id.proadress)
        prosave=view.findViewById(R.id.savepro)
        auth= FirebaseAuth.getInstance()

        proemail.isEnabled=false
        val ref = FirebaseDatabase.getInstance().getReference("users")

        prosave.setOnClickListener {
            ref.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    TODO("Not yet implemented")
                }

                override fun onDataChange(p0: DataSnapshot) {



                    for (y in p0.children) {
                        if (y.child("email").getValue()
                                .toString() == auth.currentUser?.email.toString()
                        ) {
                            ref.child("${y.key}").child("fullname").setValue("${proname.text}")
                            ref.child("${y.key}").child("mobileno").setValue("${promobile.text}")
                            ref.child("${y.key}").child("gender").setValue("${progender.text}")
                            ref.child("${y.key}").child("adress").setValue("${proadress.text}")


                        }
                    }
                    Toast.makeText(context,"Successfull",Toast.LENGTH_SHORT).show()

                }
            })



        }










        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(p0: DataSnapshot) {



                for (y in p0.children) {
                    if (y.child("email").getValue()
                            .toString() == auth.currentUser?.email.toString()
                    ) {
                        proname.setText(y.child("fullname").getValue().toString())
                        proemail.setText(y.child("email").getValue().toString())
                        promobile.setText(y.child("mobileno").getValue().toString())
                        progender.setText(y.child("gender").getValue().toString())
                        proadress.setText(y.child("adress").getValue().toString())

                    }
                }

            }
        })




        return view
    }

}
