package com.adpratap11.dmrcp1


import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import kotlinx.android.synthetic.main.activity_attendence.*
import android.net.ConnectivityManager
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast


val REQUEST_IMAGE_CAPTURE = 1

val MY_PERMISSIONS_REQUEST_CM = 0


class attendence : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attendence)

        val progressBar: ProgressBar = this.progressBar3




        profilepic.setOnClickListener {


            if (net()) {




                if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.CAMERA
                    )
                    != PackageManager.PERMISSION_GRANTED
                ) {

                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            this,
                            Manifest.permission.CAMERA
                        )
                    ) {

                    } else {


                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(Manifest.permission.CAMERA),
                            MY_PERMISSIONS_REQUEST_CM
                        )


                    }
                } else {
                    // Permission has already been granted

                    dispatchTakePictureIntent()
                }


            }
            else{

                val toast = Toast.makeText(applicationContext, "no internet", Toast.LENGTH_SHORT)
                toast.show()

            }
        }



        //upload

        button_upload.setOnClickListener {
            if (net()){

                progressBar.visibility = View.VISIBLE



            }else{

                val toast = Toast.makeText(applicationContext, "no internet", Toast.LENGTH_SHORT)
                toast.show()

            }
        }









    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            profilepic.setImageBitmap(imageBitmap)
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






}
