package com.adpratap11.dmrcp1

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_admin.*

val useui = FirebaseAuth.getInstance().currentUser

class admin : AppCompatActivity() {

    lateinit var datalist: MutableList<model>
    lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)


        datalist = mutableListOf()
        listView = findViewById(R.id.listView)

        if (useui != null) {

            loadData()
        } else {

            Toast.makeText(applicationContext, "sorry no user", Toast.LENGTH_SHORT).show()

        }


    }

    private fun loadData() {

        val ref = FirebaseDatabase.getInstance().getReference("DMRC USERS DAILY DATA/" + useui!!.uid)
        ref.keepSynced(true)


        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

                Toast.makeText(applicationContext, "snapshot error", Toast.LENGTH_SHORT).show()


            }

            override fun onDataChange(p0: DataSnapshot) {


                if (p0.exists()) {

                    for (h in p0.children) {


                        val record = h.getValue(model::class.java)
                        datalist.add(record!!)
                        val mmm = h.child("name").value.toString()
                        historyname.text = "$mmm"
                        val depter = DataAdepter(applicationContext, R.layout.olddata, datalist)
                        listView.adapter = depter


                    }


                }
            }


        })


    }



}

