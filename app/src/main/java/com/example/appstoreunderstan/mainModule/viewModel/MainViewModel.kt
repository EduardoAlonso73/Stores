package com.example.appstoreunderstan.mainModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appstoreunderstan.common.entities.StoreEntity
import com.example.appstoreunderstan.common.utils.Constants
import com.example.appstoreunderstan.mainModule.model.MainInteraction
import kotlinx.coroutines.launch


class MainViewModel:ViewModel() {
    private    var storeList:MutableList<StoreEntity> = mutableListOf()
    private  var interactor:MainInteraction= MainInteraction()
    private  val showProgress:MutableLiveData<Boolean> = MutableLiveData()

    // lazy is other way to initialization  variable of type --val--
    /* private val  store:MutableLiveData<MutableList<StoreEntity>>  by lazy { MutableLiveData<MutableList<StoreEntity>>()
         .also {  loadStore() } }*/

    private  val store=interactor.stores

    fun getStores():LiveData<MutableList<StoreEntity>> =store


    // fun isShowProgressBar():LiveData<Boolean> =showProgress

/*    private fun loadStore(){
        showProgress.value=Constants.SHOW //Inicia apiori mostrando el progress bar
        interactor.getStore {
            showProgress.value=Constants.HIDE
            store.value=it
            storeList=it
        }

    }*/

    fun deleteStore(storeEntity: StoreEntity){
        interactor.deleteStore(storeEntity){
            val index = storeList.indexOf(storeEntity)
            if (index!= -1){
                storeList.removeAt(index)
                //   store.value=storeList
            }
        }
    }


    fun updateStoreFavorite(storeEntity: StoreEntity){
        viewModelScope.launch {
            storeEntity.isFavorite = !storeEntity.isFavorite
            interactor.updateStoreFavorite(storeEntity)

        }

    }




}


