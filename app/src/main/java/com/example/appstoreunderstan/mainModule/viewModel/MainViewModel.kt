package com.example.appstoreunderstan.mainModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appstoreunderstan.common.entities.StoreEntity
import com.example.appstoreunderstan.common.utils.Constants
import com.example.appstoreunderstan.common.utils.StoresExceptio
import com.example.appstoreunderstan.common.utils.TypeError
import com.example.appstoreunderstan.mainModule.model.MainInteraction
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class MainViewModel:ViewModel() {
    private  var interactor:MainInteraction= MainInteraction()
    private  val showProgress:MutableLiveData<Boolean> = MutableLiveData()
    private  val typeError:MutableLiveData<TypeError> = MutableLiveData()


    private  val store=interactor.listAllStores

    fun getStores():LiveData<MutableList<StoreEntity>> =store //🚀 👀
    fun isShowProgressBar():LiveData<Boolean> =showProgress
    fun getTypeError():MutableLiveData<TypeError> = typeError

    fun deleteStore(storeEntity: StoreEntity) {
        executeAction {  interactor.deleteStore(storeEntity) }
    }

    fun updateStoreFavorite(storeEntity: StoreEntity) {
        storeEntity.isFavorite = !storeEntity.isFavorite
        executeAction {interactor.updateStoreFavorite(storeEntity)  }

    }

    private fun executeAction(block:suspend () -> Unit):Job{
        showProgress.value=Constants.SHOW
        return viewModelScope.launch {
            try {
                block()
            }catch (e:StoresExceptio){
                typeError.value=e.typeError
            }
            finally {
                showProgress.value=Constants.HIDE
            }
        }
    }
}



