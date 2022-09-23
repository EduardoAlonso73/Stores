package com.example.appstoreunderstan.mainModule.model

import android.widget.Toast
import com.example.appstoreunderstan.StoreApplication
import com.example.appstoreunderstan.common.entities.StoreEntity
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainInteraction {

    fun getStore(callback: (MutableList<StoreEntity>)-> Unit){
        doAsync {
            val storeList = StoreApplication.database.storeDoa().getListAllStore()
            uiThread {callback(storeList)}
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