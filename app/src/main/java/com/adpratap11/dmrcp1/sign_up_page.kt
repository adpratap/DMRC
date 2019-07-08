package com.adpratap11.dmrcp1

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_up_page.*
import java.util.*



class sign_up_page : AppCompatActivity() {


    lateinit var user : EditText
    lateinit var email : EditText
    lateinit var password : EditText




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_page)


        user = findViewById(R.id.usrname_R)
        password = findViewById(R.id.password_R)
        email = findViewById(R.id.email_R)



        button_signup.setOnClickListener {

            if (net()) {


                progressBarsignup.visibility = View.VISIBLE

                val name = user.text.toString()
                val pass = password.text.toString()
                val email = email.text.toString()


                if (email.isEmpty() && pass.isEmpty() && name.isEmpty()) {

                    progressBarsignup.visibility = View.GONE


                    val toast = Toast.makeText(applicationContext, "ERROR EMPTY FILLEDS", Toast.LENGTH_SHORT)
                    toast.show()
                    return@setOnClickListener
                }

                else{

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, pass).addOnCompleteListener {

                    //Toast.makeText(applicationContext, "user created", Toast.LENGTH_SHORT).show()
                    datauploadd()



                }.addOnCanceledListener {
                    progressBarsignup.visibility = View.GONE
                    val toast = Toast.makeText(applicationContext, "ERROR", Toast.LENGTH_SHORT)
                    toast.show()
                    return@addOnCanceledListener

                }

                }

            }


            else{


                Toast.makeText(applicationContext, "no internet", Toast.LENGTH_SHORT).show()

            }



        }



        btnsignin.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)

        }



    }

    private fun datauploadd(){



        val ref = FirebaseDatabase.getInstance().getReference("DMRC USERS LIST")
        val name = user.text.toString().trim()
        val emaiil = email.text.toString().trim()
        val timedate = getCurrentDateTime().toString()
        val userids = FirebaseAuth.getInstance().currentUser!!.uid




            //uploading data
            val dmrcuser = DMRCUSER(userids,name,timedate,emaiil)
            ref.child(userids).setValue(dmrcuser)
                .addOnCompleteListener {

                    Toast.makeText(applicationContext, "sign up done", Toast.LENGTH_SHORT).show()

                    progressBarsignup.visibility = View.GONE

                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    return@addOnCompleteListener


                }.addOnFailureListener {



                    Toast.makeText(applicationContext, "error upload data", Toast.LENGTH_SHORT).show()
                    return@addOnFailureListener

                }.addOnCanceledListener {


                    Toast.makeText(applicationContext, "error upload data canceled", Toast.LENGTH_SHORT).show()
                    return@addOnCanceledListener

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

    fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }

}
