package com.example.appstoreunderstan.mainModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.example.appstoreunderstan.common.entities.StoreEntity
import com.example.appstoreunderstan.mainModule.model.MainInteractor


class MainViewModel:ViewModel() {
    private    var storeList:MutableList<StoreEntity> = mutableListOf()
    private  var interactor:MainInteractor= MainInteractor()

    // lazy is other way to initialization  variable of type --val--
    private val  store:MutableLiveData<List<StoreEntity>>  by lazy { MutableLiveData<List<StoreEntity>>()
        .also {  loadStore() } }


    fun getStores():LiveData<List<StoreEntity>> =store


    private fun loadStore(){
        interactor.getStore {
            store.value=it
            storeList=it
        }

    }

    fun deleteStore(storeEntity: StoreEntity){
        interactor.deleteStore(storeEntity){
            val index = storeList.indexOf(storeEntity)
            if (index!= -1){
                storeList.removeAt(index)
                store.value=storeList
            }
        }
    }


    fun updateStoreFavorite(storeEntity: StoreEntity){
        storeEntity.isFavorite = !storeEntity.isFavorite
        interactor.updateStoreFavorite(storeEntity){
            val index = storeList.indexOf(storeEntity)
            if (index!= -1){
                storeList[index] = storeEntity
                store.value=storeList
            }
        }
    }


}


