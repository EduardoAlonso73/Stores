package com.example.appstoreunderstan.mainModule.model

import com.example.appstoreunderstan.StoreApplication
import com.example.appstoreunderstan.common.entities.StoreEntity
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainInteractor {

    fun getStoreCallback(store: StoresCallback){
        doAsync {
            val storeList = StoreApplication.database.storeDoa().getListAllStore()
            uiThread {store.getStoresCallback(storeList)}
        }
    }


    interface  StoresCallback{
        fun getStoresCallback (store:MutableList<StoreEntity>)
    }

}