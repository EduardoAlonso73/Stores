package com.example.appstoreunderstan.mainModule.model

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
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

    /*    fun getStore(callback: (MutableList<StoreEntity>) -> Unit){
        var storeList= mutableListOf<StoreEntity>()
        val url=Constants.STORES_URL+Constants.GET_ALL_PATH
        val jsonObjectRequest=JsonObjectRequest(Request.Method.GET,url,null,{response->
            val status =response.optInt(Constants.STATUS_PROPERTY,Constants.ERROR)
            Log.i("Response",response.toString())

            if(status == Constants.SUCCESS){
                val jsonList=response.optJSONArray(Constants.STORES_PROPERTY)?.toString()
               jsonList?.let{
                   //TypeToken is used to tell Gson what exactly you want your string to get converted to
                   val mutableListType=object:TypeToken<MutableList<StoreEntity>>(){}.type
                   storeList=Gson().fromJson(it,mutableListType)
                   println("StoreList $storeList")
                   callback(storeList)
                   return@JsonObjectRequest
               }
                callback(storeList)
            }
        },{
            it.printStackTrace()
            callback(storeList)
        })
        StoreApplication.storeAPI.addToRequestQueue(jsonObjectRequest)
    }*/
    /* fun getStoreRoom(callback: (MutableList<StoreEntity>)-> Unit){
        doAsync {
            val storeList = StoreApplication.database.storeDoa().getListAllStore()
            uiThread {
              //  val json=Gson().toJson(storeList)
               //  println(json)
                callback(storeList)
            }
        }
    }*/

    val stores:LiveData<MutableList<StoreEntity>> = liveData {
        kotlinx.coroutines.delay(1_000)
        val storesLiveData=StoreApplication.database.storeDoa().getListAllStore()
        emitSource(storesLiveData.map { stores->
            stores.sortedBy { it.name }.toMutableList()
        })

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