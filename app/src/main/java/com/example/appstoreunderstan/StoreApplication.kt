package com.example.appstoreunderstan

import android.app.Application
import androidx.room.Room

class StoreAppplication: Application() {
    companion object{
        lateinit var  database: StoreDatabase
    }
    override fun onCreate() {
        super.onCreate()
        database= Room.databaseBuilder(this,StoreDatabase::class.java,"StoreDatabase").build()

    }
}