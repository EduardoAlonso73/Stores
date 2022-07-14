package com.example.appstoreunderstan

import androidx.room.*
import com.example.stores.StoreEntity

@Dao
interface StoreDao {
    // ******* QUERY PARA MOSTRAR LOS DATOS DE LA TABLA STOREENTITY *******
        @Query("SELECT * FROM StoreEntity")
        fun  getListAllStore(): MutableList<StoreEntity>
    // ******* FUNCTION FOR ADD NUW STORES *******
        @Insert
        fun addStore(storeEntity: StoreEntity)

    // ******* FUNCTION FOR UPDATE STORES *******
        @Update
        fun updateStores(storeEntity: StoreEntity)

    // ******* FUNCTION FOR DELETE STORES *******
        @Delete
        fun deleteStore(storeEntity: StoreEntity)



}