package com.internshala.food_delivery.food_delivery.activity

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.*
import android.location.Location
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.location.LocationManagerCompat.isLocationEnabled
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import com.internshala.food_delivery.R
import com.internshala.food_delivery.food_delivery.MainActivity
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.util.*
import kotlin.collections.HashMap
import com.google.android.gms.maps.SupportMapFragment as SupportMapFragment1

@Suppress("DEPRECATION")
class mapactivity : AppCompatActivity(),OnMapReadyCallback,LocationListener{

    val PERMISSION_ALL = 1
    val PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    lateinit var map:GoogleMap

    lateinit var adress:TextView

    private lateinit var auth: FirebaseAuth



//    val pushkey=intent.getStringExtra("pushkey1")



    var mo: com.google.android.gms.maps.model.MarkerOptions? = null
    var marker: Marker? = null
    var locationManager: LocationManager? = null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapactivity)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapview) as? SupportMapFragment1
        mapFragment?.getMapAsync(this)

        // Gets to GoogleMap from the MapView and does initialization stuff

        auth = FirebaseAuth.getInstance()






//        var restadress=intent.getStringExtra("restadress")



        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        mo = com.google.android.gms.maps.model.MarkerOptions()
            .position(com.google.android.gms.maps.model.LatLng(0.0, 0.0)).title("My Current Location")





        if (Build.VERSION.SDK_INT >= 23 && !isPermissionGranted()) {
            requestPermissions(
                PERMISSIONS,
                PERMISSION_ALL
            )
        } else requestLocation()
        if (!isLocationEnabled(locationManager!!))
            showAlert(1)


            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
            } else {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions

                return
            }
        val location= locationManager!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)





        adress=findViewById(R.id.currentadress)


        val geocoder = Geocoder(this, Locale.getDefault())

        val addresses: List<Address> = geocoder.getFromLocation(location.latitude, location.longitude, 1)
        val address: String = addresses[0].getAddressLine(0)
        val city: String = addresses[0].getLocality()
        val state: String = addresses[0].getAdminArea()
        val zip: String = addresses[0].getPostalCode()
        val country: String = addresses[0].getCountryName()

        adress.text=address.toString()

        var ad:MutableMap<String, Any> = HashMap()

        ad.put("adress",address)








        val ref = FirebaseDatabase.getInstance().getReference("users")







        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(p0: DataSnapshot) {



                    for (y in p0.children) {
                        if (y.child("email").getValue()
                                .toString() == auth.currentUser?.email.toString()
                        ) {
                            ref.child(y.key!!).updateChildren(ad)
//                            Toast.makeText(this@mapactivity,"${y.key}",Toast.LENGTH_SHORT).show()
                        }
                    }

            }
        })










//        Toast.makeText(this,"$x",Toast.LENGTH_SHORT).show()





    }



    override fun onMapReady(p0: GoogleMap?) {

        var x=intent.getStringExtra("restadress")
        var adressonclick:EditText=findViewById(R.id.adresstyp)
        if (p0 != null) {
            map = p0

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
        } else {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions

            return
        }
            val location=locationManager!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)

            map.isMyLocationEnabled=true



        if (x==null) {

            map.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(
                        location.latitude, location.longitude
                    ), 15.0f
                )
            )

            marker = map.addMarker(mo)
            map.getUiSettings().setZoomControlsEnabled(true);

            var myCoordinates =
                LatLng(
                    location!!.latitude,
                    location!!.longitude
                )
            marker!!.setPosition(myCoordinates)
            map.moveCamera(CameraUpdateFactory.newLatLng(myCoordinates))
            map.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(
                        location.latitude, location.longitude
                    ), 15.0f
                )
            )

        }



            else{

            var cityy:String="Sharma and vishnu chinese,minal,Bhopal"
            var gc=Geocoder(this, Locale.getDefault())


            var adresses=gc.getFromLocationName(cityy,2)

            var adfinel=adresses.get(0)


            val location1 = LatLng(location.latitude,location.longitude)
            map.addMarker(MarkerOptions().position(location1).title("My Location"))
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(location1,13.5f))


            val location3 = LatLng(adfinel.latitude,adfinel.longitude)
            map.addMarker(MarkerOptions().position(location3).title("Baco Tale"))



            val URL = getDirectionURL(location1,location3)
            Log.d("GoogleMap", "URL : $URL")
            GetDirection(URL).execute()}













        }}

    override fun onLocationChanged(location: Location?) {

        var adressonclick:EditText=findViewById(R.id.adresstyp)

        var myCoordinates =
            LatLng(
                location!!.latitude,
                location!!.longitude
            )

        map.moveCamera(CameraUpdateFactory.newLatLng(myCoordinates))

        map.setOnMapClickListener {
            val geocoder = Geocoder(this, Locale.getDefault())

            val addresses: List<Address> = geocoder.getFromLocation(it.latitude, it.longitude, 1)
            val newaddress: String = addresses[0].getAddressLine(0)
            marker!!.position=it
            map.moveCamera(CameraUpdateFactory.newLatLng(it))
            adressonclick.setText(newaddress)

        }


//        var cityy:String="Sagar gaire,DIG bunglow,Bhopal"
//        var gc=Geocoder(this, Locale.getDefault())
//
//
//        var adresses=gc.getFromLocationName(cityy,2)
//
//        var adfinel=adresses.get(0)


//
//        map.moveCamera(CameraUpdateFactory.newLatLng(LatLng(adfinel.latitude,adfinel.longitude)))
//        map.animateCamera(
//            CameraUpdateFactory.newLatLngZoom(
//                LatLng(
//                    adfinel.latitude,adfinel.longitude
//                ), 15.0f
//            )
//        )
//        marker!!.position= LatLng(adfinel.latitude,adfinel.longitude)




//        map.animateCa;mera(CameraUpdateFactory.newLatLngZoom(myCoordinates, 10.0F))

    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun onProviderEnabled(provider: String?) {
        TODO("Not yet implemented")
    }

    override fun onProviderDisabled(provider: String?) {
        TODO("Not yet implemented")
    }

    private fun requestLocation() {
        val criteria = Criteria()
        criteria.accuracy = Criteria.ACCURACY_FINE
        criteria.powerRequirement = Criteria.POWER_HIGH
        val provider: String = locationManager?.getBestProvider(criteria, true) !!
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }
        locationManager!!.requestLocationUpdates(provider, 10000, 10f, this)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun isPermissionGranted(): Boolean {
        return if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
            === PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            === PackageManager.PERMISSION_GRANTED
        ) {
            Log.v("mylog", "Permission is granted")
            true
        } else {
            Log.v("mylog", "Permission not granted")
            false
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showAlert(status: Int) {
        val message: String
        val title: String
        val btnText: String
        if (status == 1) {
            message = """
                Your Locations Settings is set to 'Off'.
                Please Enable Location to use this app
                """.trimIndent()
            title = "Enable Location"
            btnText = "Location Settings"
        } else {
            message = "Please allow this app to access location!"
            title = "Permission access"
            btnText = "Grant"
        }
        val dialog: AlertDialog.Builder = AlertDialog.Builder(this)
        dialog.setCancelable(false)
        dialog.setTitle(title)
            .setMessage(message)
            .setPositiveButton(btnText,
                DialogInterface.OnClickListener { paramDialogInterface, paramInt ->
                    if (status == 1) {
                        val myIntent =
                            Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                        startActivity(myIntent)
                    } else requestPermissions(
                        PERMISSIONS,
                        PERMISSION_ALL
                    )
                })
            .setNegativeButton("Cancel",
                DialogInterface.OnClickListener { paramDialogInterface, paramInt -> finish() })
        dialog.show()
    }

    override fun onBackPressed() {

        startActivity(Intent(this,MainActivity::class.java))
        super.onBackPressed()
    }


    fun getDirectionURL(origin:LatLng,dest:LatLng) : String{
        return "https://maps.googleapis.com/maps/api/directions/json?origin=${origin.latitude},${origin.longitude}&destination=${dest.latitude},${dest.longitude}&sensor=false&mode=driving"
    }

    private inner class GetDirection(val url : String) : AsyncTask<Void, Void, List<List<LatLng>>>(){
        override fun doInBackground(vararg params: Void?): List<List<LatLng>> {
            val client = OkHttpClient()
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            val data = response.body()!!.string()
            Log.d("GoogleMap" , " data : $data")
            val result =  ArrayList<List<LatLng>>()
            try{
                val respObj = Gson().fromJson(data,GoogleMapDTO::class.java)

                val path =  ArrayList<LatLng>()

                for (i in 0..(respObj.routes[0].legs[0].steps.size-1)){
                    val startLatLng = LatLng(respObj.routes[0].legs[0].steps[i].start_location.lat.toDouble()
                            ,respObj.routes[0].legs[0].steps[i].start_location.lng.toDouble())
                    path.add(startLatLng)
                    val endLatLng = LatLng(respObj.routes[0].legs[0].steps[i].end_location.lat.toDouble()
                            ,respObj.routes[0].legs[0].steps[i].end_location.lng.toDouble())
//                    path.addAll(decodePolyline(respObj.routes[0].legs[0].steps[i].polyline.points))
                }
                result.add(path)
            }catch (e:Exception){
                e.printStackTrace()
            }
            return result
        }

        override fun onPostExecute(result: List<List<LatLng>>) {
            val lineoption = PolylineOptions()
            for (i in result.indices){
                lineoption.addAll(result[i])
                lineoption.width(10f)
                lineoption.color(Color.BLACK)
                lineoption.geodesic(true)
            }
            map.addPolyline(lineoption)
        }
    }

    public fun decodePolyline(encoded: String): List<LatLng> {

        val poly = ArrayList<LatLng>()
        var index = 0
        val len = encoded.length
        var lat = 0
        var lng = 0

        while (index < len) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat

            shift = 0
            result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng

            val latLng = LatLng((lat.toDouble() / 1E5),(lng.toDouble() / 1E5))
            poly.add(latLng)
        }

        return poly
    }




}











