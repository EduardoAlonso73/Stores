package com.example.appstoreunderstan.editModel.model

import androidx.lifecycle.LiveData
import com.example.appstoreunderstan.StoreApplication
import com.example.appstoreunderstan.common.entities.StoreEntity
import com.example.appstoreunderstan.common.utils.StoresExceptio
import com.example.appstoreunderstan.common.utils.TypeError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class EditStoreInteractor {

    fun getStoreById(id:Long): LiveData<StoreEntity> {
        return  StoreApplication.database.storeDoa().getStoreById(id)
    }

    suspend fun saveStore(storeEntity: StoreEntity )= withContext(Dispatchers.IO){
        val result=StoreApplication.database.storeDoa().addStore(storeEntity)
        if(result==0L) throw  StoresExceptio(TypeError.INSERT)
    }


    suspend   fun updateStore(storeEntity: StoreEntity)= withContext(Dispatchers.IO){
        val result=StoreApplication.database.storeDoa().updateStores(storeEntity)
        if(result==0) throw  StoresExceptio(TypeError.UPDATE)
    }


}