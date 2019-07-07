package com.adpratap11.dmrcp1

import android.app.Application

import com.google.firebase.database.FirebaseDatabase


    class DMRCP1 : Application() {



        override fun onCreate() {
            super.onCreate()

            FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        }

    }