package com.example.appstoreunderstan.editModel.model

import androidx.lifecycle.LiveData
import com.example.appstoreunderstan.StoreApplication
import com.example.appstoreunderstan.common.entities.StoreEntity
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class EditStoreInteractor {

    fun getStoreById(id:Long): LiveData<StoreEntity> {
        return  StoreApplication.database.storeDoa().getStoreById(id)
    }

    fun saveStore(storeEntity: StoreEntity, callback: (Long) -> Unit){
        doAsync {
            val newId = StoreApplication.database.storeDoa().addStore(storeEntity)
           // StoreApplication.database.storeDoa().addStore(storeEntity)
            uiThread {callback(newId)}
        }
    }


    fun updateStore(storeEntity: StoreEntity, callback: (StoreEntity) -> Unit){
        doAsync {
            StoreApplication.database.storeDoa().updateStores(storeEntity)
            uiThread {callback(storeEntity)}
        }
    }


}