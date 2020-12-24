package com.internshala.food_delivery.food_delivery.activity

import android.app.Activity
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.internshala.food_delivery.R
import android.content.Context as Context1


class payment : AppCompatActivity() {

    lateinit var proceedpayment:Button
    lateinit var paytype: String
    lateinit var cash: RadioButton
    lateinit var gpay: RadioButton
    lateinit var group1: RadioGroup

//    var GOOGLE_PAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user"
//    var GOOGLE_PAY_REQUEST_CODE = 123
    val UPI_PAYMENT = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        proceedpayment=findViewById(R.id.paymentproceed)
        cash=findViewById(R.id.paymentcash)
        gpay=findViewById(R.id.paymentgpay)
        group1=findViewById(R.id.radiopayment)


        group1.setOnCheckedChangeListener { _, checkedId ->
            //if catButton was selected add 1 to variable cat
            if (checkedId == R.id.paymentcash) {
                paytype = cash.text.toString()
//                Toast.makeText(this@signup, "$gender", Toast.LENGTH_SHORT).show()
            }
            //if dogButton was selected add 1 to variable dog
            if (checkedId == R.id.paymentgpay) {

                paytype = gpay.text.toString()
//                Toast.makeText(this@signup, "$gender", Toast.LENGTH_SHORT).show()

            }
        }


        var amount=intent.getStringExtra("amount")
//        Toast.makeText(this,"$amount",Toast.LENGTH_SHORT).show()

        proceedpayment.setOnClickListener(){
            if (paytype=="CASH ON DELIVERY")
            {val intent = Intent(this, splash::class.java)
                intent.putExtra("amount",amount)
                startActivity(intent)}
            else
            {
//                val intent = Intent(this, splash::class.java)
//                intent.putExtra("amount",amount)
//                startActivity(intent)

                payUsingUpi(amount,"9977533596@paytm");

//                Toast.makeText(this,"$amount",Toast.LENGTH_SHORT).show()



            }
        }

    }


    fun payUsingUpi(
        amount:String,upiId:String
    ) {



        Log.e("main ", "name -$amount")
        val uri: Uri = Uri.parse("upi://pay").buildUpon()
            .appendQueryParameter("pa", upiId)
//            .appendQueryParameter("pn", name)
            .appendQueryParameter("mc", "j")
            .appendQueryParameter("tid", "02125412")
            .appendQueryParameter("tr", "25584584")
//            .appendQueryParameter("tn", note)
            .appendQueryParameter("am", amount)
            .appendQueryParameter("cu", "INR")
            .appendQueryParameter("refUrl", "blueapp")
            .build()


        val upiPayIntent = Intent(Intent.ACTION_VIEW)
        upiPayIntent.data = uri
        // will always show a dialog to user to choose an app
        // will always show a dialog to user to choose an app
        val chooser = Intent.createChooser(upiPayIntent, "Pay with")
        // check if intent resolves
        // check if intent resolves
        if (null != chooser.resolveActivity(packageManager)) {
            startActivityForResult(chooser, UPI_PAYMENT)
        } else {
            Toast.makeText(
                this@payment,
                "No UPI app found, please install one to continue",
                Toast.LENGTH_SHORT
            ).show()
        }


    }


    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.e("main ", "response $resultCode")
        when (requestCode) {
            UPI_PAYMENT -> if (Activity.RESULT_OK == resultCode || resultCode == 11) {
                if (data != null) {
                    val trxt = data.getStringExtra("response")
                    Log.e("UPI", "onActivityResult: $trxt")
                    val dataList: ArrayList<String> = ArrayList()
                    dataList.add(trxt)
                    upiPaymentDataOperation(dataList)
                } else {
                    Log.e("UPI", "onActivityResult: " + "Return data is null")
                    val dataList: ArrayList<String> = ArrayList()
                    dataList.add("nothing")
                    upiPaymentDataOperation(dataList)
                }
            } else {
                //when user simply back without payment
                Log.e("UPI", "onActivityResult: " + "Return data is null")
                val dataList: ArrayList<String> = ArrayList()
                dataList.add("nothing")
                upiPaymentDataOperation(dataList)
            }
        }
    }



    private fun upiPaymentDataOperation(data: ArrayList<String>) {
        if (isConnectionAvailable(this@payment)) {
            var str = data[0]
            Log.e("UPIPAY", "upiPaymentDataOperation: $str")
            var paymentCancel = ""
            if (str == null) str = "discard"
            var status = ""
            var approvalRefNo = ""
            val response = str.split("&").toTypedArray()
            for (i in response.indices) {
                val equalStr =
                    response[i].split("=").toTypedArray()
                if (equalStr.size >= 2) {
                    if (equalStr[0].toLowerCase() == "Status".toLowerCase()) {
                        status = equalStr[1].toLowerCase()
                    } else if (equalStr[0]
                            .toLowerCase() == "ApprovalRefNo".toLowerCase() || equalStr[0]
                            .toLowerCase() == "txnRef".toLowerCase()
                    ) {
                        approvalRefNo = equalStr[1]
                    }
                } else {
                    paymentCancel = "Payment cancelled by user."
                }
            }
            if (status == "success") {
                //Code to handle successful transaction here.
                Toast.makeText(this@payment, "Transaction successful.", Toast.LENGTH_SHORT)
                    .show()
                Log.e("UPI", "payment successfull: $approvalRefNo")
            } else if ("Payment cancelled by user." == paymentCancel) {
                Toast.makeText(this@payment, "Payment cancelled by user.", Toast.LENGTH_SHORT)
                    .show()
                Log.e("UPI", "Cancelled by user: $approvalRefNo")
            } else {
                Toast.makeText(
                    this@payment,
                    "Transaction failed.Please try again",
                    Toast.LENGTH_SHORT
                ).show()
                Log.e("UPI", "failed payment: $approvalRefNo")
            }
        } else {
            Log.e("UPI", "Internet issue: ")
            Toast.makeText(
                this@payment,
                "Internet connection is not available. Please check and try again",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun isConnectionAvailable(context: Context1): Boolean {
        val connectivityManager =
            context.getSystemService(Context1.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val netInfo = connectivityManager.activeNetworkInfo
            if (netInfo != null && netInfo.isConnected
                && netInfo.isConnectedOrConnecting
                && netInfo.isAvailable
            ) {
                return true
            }
        }
        return false
    }


}
