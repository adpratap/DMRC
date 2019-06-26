package com.adpratap11.dmrcp1

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_sign_up_page.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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


                        val toast = Toast.makeText(applicationContext, "success now you loged in", Toast.LENGTH_SHORT)
                        toast.show()

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
}
