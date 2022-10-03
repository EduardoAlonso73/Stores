package com.example.appstoreunderstan.mainModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appstoreunderstan.common.entities.StoreEntity
import com.example.appstoreunderstan.common.utils.Constants
import com.example.appstoreunderstan.common.utils.TypeError
import com.example.appstoreunderstan.mainModule.model.MainInteraction
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class MainViewModel:ViewModel() {
    private    var storeList:MutableList<StoreEntity> = mutableListOf()
    private  var interactor:MainInteraction= MainInteraction()
    private  val showProgress:MutableLiveData<Boolean> = MutableLiveData()
    private  val typeError:MutableLiveData<TypeError> = MutableLiveData()
    // lazy is other way to initialization  variable of type --val--
    /* private val  store:MutableLiveData<MutableList<StoreEntity>>  by lazy { MutableLiveData<MutableList<StoreEntity>>()
         .also {  loadStore() } }*/

    private  val store=interactor.stores

    fun getStores():LiveData<MutableList<StoreEntity>> =store //🚀 👀
    fun isShowProgressBar():LiveData<Boolean> =showProgress
    fun getTypeError():MutableLiveData<TypeError> = typeError




    /*    private fun loadStore(){
    showProgress.value=Constants.SHOW //Inicia apiori mostrando el progress bar
    interactor.getStore {
        showProgress.value=Constants.HIDE
        store.value=it
        storeList=it
    }

}*/

    fun deleteStore(storeEntity: StoreEntity) {
        executeAction {  interactor.deleteStore(storeEntity) }
        /*
            viewModelScope.launch {
                interactor.deleteStore(storeEntity)
            }*/
    }

    fun updateStoreFavorite(storeEntity: StoreEntity) {
        storeEntity.isFavorite = !storeEntity.isFavorite
        executeAction {interactor.updateStoreFavorite(storeEntity)  }
        /*  viewModelScope.launch {
              showProgress.value=Constants.SHOW

              try {
                  storeEntity.isFavorite = !storeEntity.isFavorite
                  interactor.updateStoreFavorite(storeEntity)
              }catch (e:Exception){
                  e.printStackTrace()
              }
              finally {
                  showProgress.value=Constants.HIDE
              }

          }*/

    }

    private fun executeAction(block:suspend () -> Unit):Job{
        showProgress.value=Constants.SHOW
        return viewModelScope.launch {
            try {
                block()
            }catch (e:Exception){
                e.printStackTrace()
            }
            finally {
                showProgress.value=Constants.HIDE
            }
        }
    }
}



