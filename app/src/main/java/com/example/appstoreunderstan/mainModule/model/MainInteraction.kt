package com.example.appstoreunderstan.mainModule.model
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.example.appstoreunderstan.StoreApplication
import com.example.appstoreunderstan.common.entities.StoreEntity
import com.example.appstoreunderstan.common.utils.StoresExceptio
import com.example.appstoreunderstan.common.utils.TypeError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

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
        delay(1_000)
        val storesLiveData=StoreApplication.database.storeDoa().getListAllStore()
        emitSource(storesLiveData.map { stores->
            stores.sortedBy { it.name }.toMutableList()
        })

    }
    suspend fun deleteStore(storeEntity: StoreEntity)= withContext(Dispatchers.IO){
        delay(2_000)
        val result=StoreApplication.database.storeDoa().deleteStore(storeEntity)
        if(result==0) throw  StoresExceptio(TypeError.DELETE)

    }


    suspend fun  updateStoreFavorite(storeEntity:StoreEntity) = withContext(Dispatchers.IO){
        delay(2_000)
        val result= StoreApplication.database.storeDoa().updateStores(storeEntity)
        if (result==0) throw  StoresExceptio(TypeError.UPDATE)

        /* storeEntity.isFavorite = !storeEntity.isFavorite
         doAsync {

             uiThread { callback(storeEntity)}
         }*/
    }


}

