package com.adpratap11.dmrcp1


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_admin_dmrc.*
import java.text.SimpleDateFormat
import java.util.*

//lateinit var d: String
//lateinit var m: String
//lateinit var y: String


class AdminDmrc : AppCompatActivity() {

    lateinit var admindatalist: MutableList<model>
    lateinit var adminlistView: ListView
    lateinit var dateinputt: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dmrc)

        //val cal = Calendar.getInstance()
        //val dd = cal.get(Calendar.DAY_OF_MONTH)
        //val mm = cal.get(Calendar.MONTH)
        //val yy = cal.get(Calendar.YEAR)

        admindatalist = mutableListOf()
        adminlistView = findViewById(R.id.dmrclistview)


        /* dateinputlayout.setOnClickListener {

             val datenew = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                 d = dayOfMonth.toString()
                 m = month.toString()
                 y = year.toString()
             }, yy, mm, dd)

             // datenew.show()

         }
         */



        btnfind.setOnClickListener {

            dateinputt = dateinputlayout.text.toString()


            if (dateinputt != "") {


                loaddates()

            } else {


                Toast.makeText(applicationContext, " date error", Toast.LENGTH_SHORT).show()


            }
        }
    }

    private fun loaddates() {

        progressBarAdmindata.visibility = View.VISIBLE


        val adminref = FirebaseDatabase.getInstance().getReference("FOR ADMIN/" + dateinputt)
        adminref.keepSynced(true)


        adminref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

                Toast.makeText(applicationContext, "snapshot error", Toast.LENGTH_SHORT).show()


            }

            override fun onDataChange(p0: DataSnapshot) {


                if (p0.exists()) {

                    Toast.makeText(applicationContext, "date done starting if ", Toast.LENGTH_SHORT).show()

                    for (h in p0.children) {

                        Toast.makeText(applicationContext, "date done starting for", Toast.LENGTH_SHORT).show()

                        val recordd = h.getValue(model::class.java)
                        admindatalist.add(recordd!!)
                        val depterr = DataAdepter(applicationContext, R.layout.olddata, admindatalist)
                        dmrclistview.adapter = depterr


                    }

                    progressBarAdmindata.visibility = View.GONE


                }
            }


        })


    }

    fun datev(): String {

        val sdf = SimpleDateFormat("dd MM yyyy")
        val currentDate = sdf.format(Date())
        return currentDate
    }


}

