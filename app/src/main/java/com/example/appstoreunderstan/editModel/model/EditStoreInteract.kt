package com.example.appstoreunderstan.editModel.model

import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.LiveData
import com.example.appstoreunderstan.StoreApplication
import com.example.appstoreunderstan.common.entities.StoreEntity
import com.example.appstoreunderstan.common.utils.StoresExceptio
import com.example.appstoreunderstan.common.utils.TypeError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class EditStoreInteract {

    fun getStoreById(id:Long): LiveData<StoreEntity> {
        return  StoreApplication.database.storeDoa().getStoreById(id)
    }

    suspend fun saveStore(storeEntity: StoreEntity )= withContext(Dispatchers.IO){
        try {
            StoreApplication.database.storeDoa().addStore(storeEntity)
        }catch (e:SQLiteConstraintException){
            throw  StoresExceptio(TypeError.INSERT)
        }

    }

    suspend   fun updateStore(storeEntity: StoreEntity)= withContext(Dispatchers.IO){
        try {
            StoreApplication.database.storeDoa().updateStores(storeEntity)
        } catch (e:SQLiteConstraintException){
            throw  StoresExceptio(TypeError.UPDATE)
        }
    }


}