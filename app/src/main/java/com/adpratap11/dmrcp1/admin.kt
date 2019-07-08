package com.adpratap11.dmrcp1

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.FirebaseDatabase


class admin : AppCompatActivity() {


    val rf = FirebaseDatabase.getInstance().getReference("DMRC USERS DAILY DATA")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)


    }




}

