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
    val listAllStores:LiveData<MutableList<StoreEntity>> = liveData {
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

    }


}

