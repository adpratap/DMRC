package com.adpratap11.dmrcp1

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
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

        val progressBar: ProgressBar = this.progressBar


        button_signup.setOnClickListener {

            if (net()) {


                progressBar.visibility = View.VISIBLE

                val name = user.text.toString()
                val pass = password.text.toString()
                val email = email.text.toString()

                if (email.isEmpty() || pass.isEmpty() || name.isEmpty()) {

                    progressBar.visibility = View.GONE


                    val toast = Toast.makeText(applicationContext, "ERROR EMPTY FILLEDS", Toast.LENGTH_SHORT)
                    toast.show()
                    return@setOnClickListener
                }

                else{

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, pass).addOnCompleteListener {

                    val toast = Toast.makeText(applicationContext, "user created", Toast.LENGTH_SHORT)
                    toast.show()

                    datauploadd()


                }.addOnCanceledListener {
                    progressBar.visibility = View.GONE
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

        val name = user.text.toString().trim()
        val emaiil = email.text.toString().trim()
        val timedate = getCurrentDateTime().toString()
        val ref = FirebaseDatabase.getInstance().getReference("DMRC USERS LIST")
        val id =  ref.push().key.toString()
        val user = FirebaseAuth.getInstance().currentUser

        if(name.isEmpty()||emaiil.isEmpty()) {


            Toast.makeText(applicationContext, "plz enter data", Toast.LENGTH_SHORT).show()


        }else{

            //uploading data
            val dmrcuser = DMRCUSER(id,name,timedate,emaiil)
            ref.child(id).setValue(dmrcuser)
                .addOnCompleteListener {

                    Toast.makeText(applicationContext, "SUCCESS upload data", Toast.LENGTH_SHORT).show()
                    progressBar.visibility = View.GONE
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)


                }.addOnFailureListener {
                    progressBar.visibility = View.GONE
                    Toast.makeText(applicationContext, "error upload data", Toast.LENGTH_SHORT).show()
                    return@addOnFailureListener
                }.addOnCanceledListener {
                    progressBar.visibility = View.GONE
                    Toast.makeText(applicationContext, "error upload data canceled", Toast.LENGTH_SHORT).show()
                    return@addOnCanceledListener
                }

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
