package com.example.appstoreunderstan.mainModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.example.appstoreunderstan.common.entities.StoreEntity
import com.example.appstoreunderstan.mainModule.model.MainInteractor


class MainViewModel:ViewModel() {
    private var  store:MutableLiveData<List<StoreEntity>> = MutableLiveData()
    private  var interactor:MainInteractor= MainInteractor()

    init { this.setLoadStore() }

    fun getStores():LiveData<List<StoreEntity>> =store

    private fun setLoadStore(){
    interactor.getStoreCallback(object :MainInteractor.StoresCallback{
        override fun getStoresCallback(store: MutableList<StoreEntity>) {
            this@MainViewModel.store.value=store } })
        }

    }


