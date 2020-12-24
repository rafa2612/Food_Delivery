package com.internshala.food_delivery.food_delivery

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.firebase.auth.FirebaseAuth
import com.internshala.food_delivery.R
import com.internshala.food_delivery.food_delivery.activity.cartactivity
import com.internshala.food_delivery.food_delivery.activity.loginn
import com.internshala.food_delivery.food_delivery.activity.mapactivity
import com.internshala.food_delivery.food_delivery.fragments.cart
import com.internshala.food_delivery.food_delivery.fragments.home
import com.internshala.food_delivery.food_delivery.fragments.profile
import com.internshala.food_delivery.food_delivery.fragments.search

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    lateinit var drawerlayout: DrawerLayout
    lateinit var coordinatorlayout: CoordinatorLayout
    lateinit var toolbar: Toolbar
    lateinit var framelayout: FrameLayout
    lateinit var navigation: BottomNavigationView
    lateinit var location:TextView
    var previousmenuitem: MenuItem?=null
//    lateinit var logout:TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth=FirebaseAuth.getInstance()

//        var pushkey1=intent.getStringExtra("pushkey1")



        Toast.makeText(this,"${auth.currentUser?.email}",Toast.LENGTH_SHORT).show()




        drawerlayout=findViewById(R.id.drawerlayout)
        coordinatorlayout=findViewById(R.id.coordinatorlayout)
        toolbar=findViewById(R.id.toolbar)
        framelayout=findViewById(R.id.framelayout)
        navigation=findViewById(R.id.navbottom)
        location=findViewById(R.id.locationtoolbar)
//        logout=findViewById(R.id.logouttoolbar)

        auth=FirebaseAuth.getInstance()


        setUpToolbar()

        supportFragmentManager.beginTransaction()
            .replace(R.id.framelayout,home())
            .addToBackStack("home")
            .commit()
        supportActionBar?.title=""

        location.setOnClickListener {
//            Toast.makeText(baseContext,"location",Toast.LENGTH_SHORT).show()
            val intent = Intent(this, mapactivity::class.java)
//            intent.putExtra("pushkey1",pushkey1)
            startActivity(intent)
        }

//        logout.setOnClickListener {
//            auth.signOut()
//            finish()
//            startActivity(Intent(this,loginn::class.java))
//
//        }







        navigation.setOnNavigationItemSelectedListener{
//            if(previousmenuitem!=null){
//                previousmenuitem?.isChecked=false
//            }
//            it.isCheckable=true
//            it.isChecked=true
//            previousmenuitem=it

            when(it.itemId){
                R.id.navigation_home ->{
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.framelayout,
                            home()
                        )
                        .addToBackStack("home")

                        .commit()


                }
                R.id.navigation_search ->
                {
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.framelayout,
                            search()
                        )
                        .addToBackStack("search")
                        .commit()

                    drawerlayout.closeDrawers()
                }
                R.id.logouttoolbar ->{
                    auth.signOut()
                    finish()
                    startActivity(Intent(this,loginn::class.java))
                }
                R.id.navigation_Account ->{
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.framelayout,
                            profile()
                        )
                        .addToBackStack("profile")
                        .commit()

                    drawerlayout.closeDrawers()
                }

//                R.id.logout ->{
//                    auth.signOut()
//                    finish()
//                    startActivity(Intent(this,login::class.java))
//
//
//                }


            }
            return@setOnNavigationItemSelectedListener true
        }

    }

    fun setUpToolbar(){
        setSupportActionBar(toolbar)

//        supportActionBar?.setHomeButtonEnabled(true)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        var id=item.itemId
        if(id==android.R.id.home){
            drawerlayout.openDrawer(GravityCompat.START)
        }


        return super.onOptionsItemSelected(item)
    }


    override fun onStart() {


        super.onStart()
        if (auth.currentUser!=null){
            return
        }else{
            val intent = Intent(this, loginn::class.java)
            startActivity(intent)
        }
    }


}


