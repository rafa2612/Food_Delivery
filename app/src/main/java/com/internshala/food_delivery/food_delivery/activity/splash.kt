package com.internshala.food_delivery.food_delivery.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.internshala.food_delivery.R

class splash : AppCompatActivity() {
    lateinit var topAnim: Animation
    lateinit var txtFoodZest: TextView
    lateinit var imgAppIcon: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        var x=intent.getStringExtra("amount")

        Toast.makeText(this,"$x",Toast.LENGTH_SHORT).show()

                this.window.setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
                )
                setContentView(R.layout.activity_splash)

                //set animation for logo
                topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation)
                txtFoodZest = findViewById(R.id.txtFoodZest)
                imgAppIcon = findViewById(R.id.imgAppIcon)

                txtFoodZest.animation = topAnim
                imgAppIcon.animation = topAnim


                Handler().postDelayed({
                    val intent =
                        Intent(this@splash, mapactivity::class.java)
                    intent.putExtra("restadress",x)
                    finish()
                    startActivity(intent)
                }, 2000)
            }
        }





