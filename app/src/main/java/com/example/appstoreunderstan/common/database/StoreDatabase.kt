package com.example.appstoreunderstan.common.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.appstoreunderstan.common.database.StoreDao
import com.example.appstoreunderstan.common.entities.StoreEntity

@Database(entities= [StoreEntity::class], version = 3 )
 abstract  class StoreDatabase: RoomDatabase() {

     abstract  fun storeDoa(): StoreDao
}