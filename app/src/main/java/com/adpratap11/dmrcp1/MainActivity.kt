package com.adpratap11.dmrcp1

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

private const val PERMISSION_REQUEST = 10
private var permissions = arrayOf(
    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
    Manifest.permission.CAMERA)


class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermission(permissions)) {

                locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

                if( !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ) {

                    val toast = Toast.makeText(applicationContext, "TURN ON GPS FIRST", Toast.LENGTH_SHORT)
                    toast.show()


                    startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))


                }
               // getLocation()
            } else {
                requestPermissions(permissions, PERMISSION_REQUEST)
                locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

                if( !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ) {

                    val toast = Toast.makeText(applicationContext, "TURN ON GPS FIRST", Toast.LENGTH_SHORT)
                    toast.show()


                    startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))


                }
            }
        } else {

            locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

            if( !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ) {

                val toast = Toast.makeText(applicationContext, "TURN ON GPS FIRST", Toast.LENGTH_SHORT)
                toast.show()


                startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))


            }
            //getLocation()
        }




        auth = FirebaseAuth.getInstance()

        val progressBar: ProgressBar = this.progressBar2

        signin.setOnClickListener {


            if(net()) {

                progressBar.visibility = View.VISIBLE


                val email_login = email.text.toString()
                val pass_login = password.text.toString()

                if (email_login.isEmpty() || pass_login.isEmpty()) {


                    val toast = Toast.makeText(applicationContext, "ERROR EMPTY FILLEDS", Toast.LENGTH_SHORT)
                    toast.show()

                    progressBar.visibility = View.GONE
                    return@setOnClickListener
                } else {



                    auth.signInWithEmailAndPassword(email_login, pass_login).addOnSuccessListener {


                        progressBar.visibility = View.GONE
                        val intent = Intent(this, attendence::class.java)
                        startActivity(intent)



                    }.addOnFailureListener {

                        progressBar.visibility = View.GONE
                        val toast = Toast.makeText(
                            applicationContext,
                            "wrong password and email.. try again",
                            Toast.LENGTH_SHORT
                        )
                        toast.show()
                    }


                }
            }
            else{
                val toast = Toast.makeText(applicationContext, "no internet", Toast.LENGTH_SHORT)
                toast.show()
            }
        }


        signup.setOnClickListener {

            val intent = Intent(this,sign_up_page::class.java)
            startActivity(intent)
        }
    }


    fun net(): Boolean {
        val manager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = manager.activeNetworkInfo

        var isAvailable = false
        if (networkInfo != null && networkInfo.isConnected) {
            isAvailable = true
        }
        return isAvailable
    }

    private fun checkPermission(permissionArray: Array<String>): Boolean {
        var allSuccess = true
        for (i in permissionArray.indices) {
            if (checkCallingOrSelfPermission(permissionArray[i]) == PackageManager.PERMISSION_DENIED)
                allSuccess = false
        }
        return allSuccess
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST) {
            var allSuccess = true
            for (i in permissions.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    allSuccess = false
                    val requestAgain = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && shouldShowRequestPermissionRationale(permissions[i])
                    if (requestAgain) {
                        Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Go to settings and enable the permission", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            if (allSuccess){
                // getLocation()
                locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

                if( !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ) {

                    val toast = Toast.makeText(applicationContext, "TURN ON GPS FIRST", Toast.LENGTH_SHORT)
                    toast.show()


                    startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))


                }

            }


        }
    }




}
