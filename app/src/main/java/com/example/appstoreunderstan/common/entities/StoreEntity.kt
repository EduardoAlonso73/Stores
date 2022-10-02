package com.example.appstoreunderstan.common.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "StoreEntity")  //Convertimos  la data class en una entidad
data class StoreEntity(
                       @PrimaryKey(autoGenerate = true) var id:Long=0,
                       var name:String,
                       var phone:String,
                       var website:String="",
                       var photoUrl:String,
                       var isFavorite:Boolean=false) {
    constructor():this(name="", phone = "", photoUrl = "")

}
