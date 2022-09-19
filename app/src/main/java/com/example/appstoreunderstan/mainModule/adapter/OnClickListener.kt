package com.example.appstoreunderstan.mainModule.adapter

import com.example.appstoreunderstan.common.entities.StoreEntity

interface OnClickListener {
    fun onClick(storeEntity: StoreEntity){}
    fun onFavoriteStore(storeEntity: StoreEntity){}
    fun onDeleteStore(storeEntity: StoreEntity){}

}