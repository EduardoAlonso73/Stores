package com.example.appstoreunderstan

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.stores.StoreEntity

@Database(entities= arrayOf(StoreEntity::class), version = 2 )
 abstract  class StoreDatabase: RoomDatabase() {

     abstract  fun storeDoa():StoreDao
}