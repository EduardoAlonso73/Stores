package com.example.appstoreunderstan.common.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.appstoreunderstan.common.entities.StoreEntity

@Dao
interface StoreDao {
    // ******* QUERY PARA MOSTRAR LOS DATOS DE LA TABLA STOREENTITY *******
        @Query("SELECT * FROM StoreEntity")
        fun  getListAllStore(): LiveData<MutableList<StoreEntity>>
    // ******* FUNCTION FOR ADD NUW STORES *******
        @Insert
        suspend fun addStore(storeEntity: StoreEntity):Long

    // ******* FUNCTION FOR UPDATE STORES *******
        @Update
       suspend fun updateStores(storeEntity: StoreEntity):Int

    // ******* FUNCTION FOR DELETE STORES *******
        @Delete
       suspend fun deleteStore(storeEntity: StoreEntity):Int

    // ******* FUNCTION FOR DELETE STORES *******
        @Query("SELECT * FROM StoreEntity where id =:id")
        fun getStoreById(id:Long): LiveData<StoreEntity>



}