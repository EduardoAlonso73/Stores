package com.example.appstoreunderstan.editModel.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appstoreunderstan.common.entities.StoreEntity
import com.example.appstoreunderstan.common.utils.StoresExceptio
import com.example.appstoreunderstan.common.utils.TypeError
import com.example.appstoreunderstan.editModel.model.EditStoreInteract
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class EditStoreViewModel:ViewModel() {
    private  var storeId:Long=0
    private  val showFab=MutableLiveData<Boolean>()
    private val result=MutableLiveData<Any>()
    private  val typeError:MutableLiveData<TypeError> = MutableLiveData()


    private val  interactor:EditStoreInteract = EditStoreInteract()

    // -=-=-=-= Get and set  storeSelected =-=-=-=-=

    fun setStoreSelectored(storeEntity: StoreEntity){
        storeId=storeEntity.id
    }
    fun getStoreSelect():LiveData<StoreEntity> = interactor.getStoreById(storeId)//🚀 👀

    // -=-=-=-= Get and set  result =-=-=-=-=
    fun  setTypeError(typeError: TypeError){
        this.typeError.value=typeError
    }

    // -=-=-=-= Get and set  showFab =-=-=-=-=
    fun setShowFab(isVisible:Boolean){ this.showFab.value=isVisible }
    fun getShowFab():LiveData<Boolean> = showFab //🚀 👀


    // -=-=-=-= Get and set  result =-=-=-=-=
    fun setResult(value:Any){ this.result.value=value }
    fun getResult ():LiveData<Any> =  result //🚀 👀
    fun getTypeError():MutableLiveData<TypeError> = typeError




    // ====== Function that involves to EditStoreInteraction ======

    fun saveStore(storeEntity: StoreEntity){
     executeAction(storeEntity) { interactor.saveStore(storeEntity) }
    }

    fun updateStore(storeEntity: StoreEntity){
     executeAction(storeEntity) {interactor.updateStore(storeEntity) }
    }


    private fun executeAction(storeEntity: StoreEntity,block:suspend () -> Unit): Job {
        return viewModelScope.launch {
            try {
                block()
                result.value=storeEntity
            }catch (e: StoresExceptio){
                typeError.value=e.typeError
            }

        }
    }

}