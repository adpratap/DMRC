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
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_up_page.*

class sign_up_page : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_page)

        val progressBar: ProgressBar = this.progressBar


        button_signup.setOnClickListener {

            if (net()) {


                progressBar.visibility = View.VISIBLE

                val emal = email_R.text.toString()
                val pass = password_R.text.toString()
                val username = usrname_R.toString()

                if (emal.isEmpty() || pass.isEmpty() || username.isEmpty()) {

                    progressBar.visibility = View.GONE


                    val toast = Toast.makeText(applicationContext, "ERROR EMPTY FILLEDS", Toast.LENGTH_SHORT)
                    toast.show()
                    return@setOnClickListener
                }

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(emal, pass).addOnCompleteListener {

                    if (!it.isSuccessful) {

                        progressBar.visibility = View.GONE
                        val toast = Toast.makeText(applicationContext, "ERROR", Toast.LENGTH_SHORT)
                        toast.show()
                        return@addOnCompleteListener


                    }


                }
                uploadusr()

            }
            else{
                val toast = Toast.makeText(applicationContext, "no internet", Toast.LENGTH_SHORT)
                toast.show()

            }



        }

        btnsignin.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)

        }



    }
    private fun uploadusr(){
        val uid=FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        val user =User (uid.toString(),usrname_R.text.toString())
        ref.setValue(user)
            .addOnSuccessListener {
                progressBar.visibility = View.GONE
                val intent = Intent(this,MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)



                val toast = Toast.makeText(applicationContext, "SUCCESS NOW LOG-IN", Toast.LENGTH_SHORT)
                toast.show()
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

class User (val uid : String,val username : String)