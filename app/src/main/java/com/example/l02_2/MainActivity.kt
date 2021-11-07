package com.example.l02_2

import android.content.Context
import android.content.Intent
import android.location.Address
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.location.Geocoder
import java.lang.Exception


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button_ids = arrayOf(
            R.id.button_dial,
            R.id.button_geoloc,
            R.id.button_finish,
            R.id.button_other_app
        )
        for (id_button in button_ids) {
            findViewById<Button>(id_button).setOnClickListener(buttonListener)
        }
    }

    fun runDial(phoneNum: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNum")
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

//    fun getLocationFromAddress(context: Context?, strAddress: String?): LatLng? {
//        val coder = Geocoder(context)
//        val address: List<Address>?
//        var p1: LatLng? = null
//        try {
//            address = coder.getFromLocationName(strAddress, 5)
//            if (address == null) {
//                return null
//            }
//            val location = address[0]
//            location.latitude
//            location.longitude
//            p1 = LatLng(location.latitude, location.longitude)
//        } catch (ex: Exception) {
//            ex.printStackTrace()
//        }
//        return p1
//    }

    fun runGeoLoc(geoLoc: String) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            val geoCoder = Geocoder(baseContext)
            var addressList: List<Address>? = geoCoder.getFromLocationName(geoLoc, 1)
            val address = addressList!![0]
            val mapUri = Uri.parse("geo:${address.latitude},${address.longitude}?q=${geoLoc}")
            data = Uri.parse("geo:$mapUri")
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    val buttonListener = View.OnClickListener { v ->
        when(v.getId()) {
            R.id.button_dial -> {
                val editText = findViewById<EditText>(R.id.text_dial)
                val text = editText.text.toString()
                runDial(text)
            }
            R.id.button_geoloc -> {
                val editText = findViewById<EditText>(R.id.text_geoloc)
                val text = editText.text.toString()
                runGeoLoc(text)
            }
            R.id.button_other_app -> {
                val myAction = "pwr.ampa.exercise02.intent.action.MY_ACTION_NAME"
                val myIntent = Intent(myAction)
                startActivity(myIntent)
            }
            R.id.button_finish -> finish()
        }
    }
}