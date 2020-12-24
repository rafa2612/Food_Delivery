package com.internshala.food_delivery.food_delivery.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.internshala.food_delivery.R
import com.internshala.food_delivery.food_delivery.model.user
import kotlinx.android.synthetic.main.activity_signup.*

class signup : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    lateinit var signupbutton: Button
    lateinit var emailid: EditText
    lateinit var passward: EditText
    lateinit var phoneno: EditText
    lateinit var fullname: EditText
    lateinit var gender: String
    lateinit var male: RadioButton
    lateinit var female: RadioButton
    lateinit var group1: RadioGroup


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        signupbutton = findViewById(R.id.signupbutton)
        auth = FirebaseAuth.getInstance()
        emailid = findViewById(R.id.signupmobileno)
        passward = findViewById(R.id.signuppassward)
        phoneno = findViewById(R.id.signupphoneno)
        fullname = findViewById(R.id.signupusername)
        group1 = findViewById(R.id.radiogroupgender)
        male = findViewById(R.id.signupmale)
        female = findViewById(R.id.signupfemale)
        gender=" "

        group1.setOnCheckedChangeListener { _, checkedId ->
            //if catButton was selected add 1 to variable cat
            if (checkedId == R.id.signupmale) {
                gender = male.text.toString()
                Toast.makeText(this@signup, "$gender", Toast.LENGTH_SHORT).show()
            }
            //if dogButton was selected add 1 to variable dog
            if (checkedId == R.id.signupfemale) {

                gender = female.text.toString()
                Toast.makeText(this@signup, "$gender", Toast.LENGTH_SHORT).show()

            }
        }



        signupbutton.setOnClickListener {


            signup(gender)
        }

    }


    fun signup(x:String) {


        val username = fullname.text.toString()
        val email = emailid.text.toString()
        val phone = phoneno.text.toString()

        if (signupusername.text.toString().isEmpty()) {
            signupusername.error = "Enter your name"
            signupusername.requestFocus()
            return
        }

        if (signupmobileno.text.toString().isEmpty()) {
            signupmobileno.error = "Enter email"
            signupmobileno.requestFocus()
            return
        }

        if (signuppassward.text.toString().isEmpty()) {
            signuppassward.error = "Enter password"
            signuppassward.requestFocus()
            return
        }


        if (signupphoneno.text.toString().isEmpty()) {
            signupphoneno.error = "Enter phone no"
            signupphoneno.requestFocus()
            return
        }



//        if (signupmobileno.text.toString().isEmpty()) {
//            signupmobileno.error = "Enter email"
//            signupmobileno.requestFocus()
//            return
//        }

        if (!Patterns.EMAIL_ADDRESS.matcher(signupmobileno.text.toString()).matches()) {
            signupmobileno.error = "Enter valid email"
            signupmobileno.requestFocus()
            return
        }



        val ref = FirebaseDatabase.getInstance().getReference("users")

        val userid = ref.push().key

        val userr = user(userid!!, username, email, phone, x)

        ref.child(userid).setValue(userr).addOnCompleteListener {
//            Toast.makeText(baseContext, "done", Toast.LENGTH_SHORT).show()
        }


        auth.createUserWithEmailAndPassword(
            signupmobileno.text.toString(),
            signuppassward.text.toString()
        )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, loginn::class.java)
//                    intent.putExtra("pushkey",userid)
                    startActivity(intent)
                    finish()

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Signup failed or Email already exist", Toast.LENGTH_SHORT).show()
                }

                // ...
            }




    }






}


