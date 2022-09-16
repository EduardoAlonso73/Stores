package com.example.appstoreunderstan.mainModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appstoreunderstan.StoreApplication
import com.example.appstoreunderstan.common.entities.StoreEntity
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainViewModel:ViewModel() {
    private var  store:MutableLiveData<List<StoreEntity>> = MutableLiveData()

    init { this.setLoadStore() }

    fun getStores():LiveData<List<StoreEntity>> =store

    fun setLoadStore(){
        doAsync {
            val storeList = StoreApplication.database.storeDoa().getListAllStore()
            uiThread {
                store.value=storeList
            }
        }

    }


}