package com.example.appstoreunderstan.mainModule.model

import android.util.Log
import android.widget.Toast
import com.android.volley.Request

import com.android.volley.toolbox.JsonObjectRequest
import com.example.appstoreunderstan.StoreApplication
import com.example.appstoreunderstan.common.entities.StoreEntity
import com.example.appstoreunderstan.common.utils.Constants
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainInteraction {

    fun getStore(callback: (MutableList<StoreEntity>) -> Unit){
        val url=Constants.STORES_URL+Constants.GET_ALL_PATH
        val jsonObjectRequest=JsonObjectRequest(Request.Method.GET,url,null,{response->
          //val status =response.getInt(Constants.STATUS_PROPERTY)
            val status =response.optInt(Constants.STATUS_PROPERTY,Constants.ERROR)
            Log.i("Response",response.toString())

            if(status == Constants.SUCCESS){
                val jsonList =response.getJSONArray(Constants.STORES_PROPERTY).toString()
                println("1- jsonList ==== $jsonList")
                // TypeToken is used to tell Gson what exactly you want your string to get converted to
                val mutableListType=object:TypeToken<MutableList<StoreEntity>>(){}.type
                println("2- mutableListType ==== ${mutableListType}")
                val storeList=Gson().fromJson<MutableList<StoreEntity>>(jsonList,mutableListType)
                callback(storeList)
                println("3- StoreList ==== ${storeList}")
               // Log.i("Status ",status.toString())
            }

        },{
            it.printStackTrace()
        })
        StoreApplication.storeAPI.addToRequestQueue(jsonObjectRequest)
    }


    fun getStoreRoom(callback: (MutableList<StoreEntity>)-> Unit){
        doAsync {
            val storeList = StoreApplication.database.storeDoa().getListAllStore()
            uiThread {
              //  val json=Gson().toJson(storeList)
               //  println(json)
                callback(storeList)
            }
        }
    }

    fun deleteStore(storeEntity: StoreEntity,callback: (StoreEntity) -> Unit){
        doAsync {
            StoreApplication.database.storeDoa().deleteStore(storeEntity)
            uiThread {callback(storeEntity)}
        }
    }


    fun  updateStoreFavorite(storeEntity:StoreEntity,callback: (StoreEntity) -> Unit){
        storeEntity.isFavorite = !storeEntity.isFavorite
        doAsync {
            StoreApplication.database.storeDoa().updateStores(storeEntity)
            uiThread { callback(storeEntity)}
        }
    }

}