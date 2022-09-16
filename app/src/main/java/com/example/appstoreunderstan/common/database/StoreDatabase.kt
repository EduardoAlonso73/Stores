package com.example.appstoreunderstan.common

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.appstoreunderstan.common.entities.StoreEntity

@Database(entities= arrayOf(StoreEntity::class), version = 2 )
 abstract  class StoreDatabase: RoomDatabase() {

     abstract  fun storeDoa(): StoreDao
}