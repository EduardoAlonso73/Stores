package com.example.appstoreunderstan.editModel.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appstoreunderstan.common.entities.StoreEntity
import com.example.appstoreunderstan.editModel.model.EditStoreInteractor

class EditStoreViewModel:ViewModel() {
    private  var storeId:Long=0
    private  val storeSelected=MutableLiveData<StoreEntity>()
    private  val showFab=MutableLiveData<Boolean>()
    private val result=MutableLiveData<Any>()


    private val  interactor:EditStoreInteractor = EditStoreInteractor()

    // -=-=-=-= Get and set  storeSelected =-=-=-=-=

    fun setStoreSelectored(storeEntity: StoreEntity){
        storeId=storeEntity.id
    }
    fun getStoreSelect():LiveData<StoreEntity> = interactor.getStoreById(storeId)//🚀 👀

    /*
    fun setStoreSelectored(storeEntity: StoreEntity){
        this.storeSelected.value=storeEntity
    }
    fun getStoreSelect():LiveData<StoreEntity> = storeSelected //🚀 👀 */


    // -=-=-=-= Get and set  showFab =-=-=-=-=
    fun setShowFab(isVisible:Boolean){ this.showFab.value=isVisible }
    fun getShowFab():LiveData<Boolean> = showFab //🚀 👀


    // -=-=-=-= Get and set  result =-=-=-=-=
    fun setResult(value:Any){ this.result.value=value }
    fun getResult ():LiveData<Any> =  result //🚀 👀




    // ====== Function that involves to EditStoreInteraction ======

    fun saveStore(storeEntity: StoreEntity){
        interactor.saveStore(storeEntity){newId->
            setResult(newId)
        }
    }


    fun updateStore(storeEntity: StoreEntity){
        interactor.updateStore(storeEntity){storeUpdated->
            setResult(storeUpdated)
        }
    }


}