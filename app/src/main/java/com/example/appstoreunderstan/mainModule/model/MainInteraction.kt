package com.example.appstoreunderstan.mainModule.model

import android.util.Log
import android.widget.Toast
import com.android.volley.Request

import com.android.volley.toolbox.JsonObjectRequest
import com.example.appstoreunderstan.StoreApplication
import com.example.appstoreunderstan.common.entities.StoreEntity
import com.example.appstoreunderstan.common.utils.Constants
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainInteraction {


    fun getStore(callback: (MutableList<StoreEntity>) -> Unit){
        val url=Constants.STORES_URL+Constants.GET_ALL_PATH
        val jsonObjectRequest=JsonObjectRequest(Request.Method.GET,url,null,{response->
            Log.i("Response",response.toString())

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