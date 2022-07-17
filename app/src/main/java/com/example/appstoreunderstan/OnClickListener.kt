package com.example.appstoreunderstan

import com.example.stores.StoreEntity

interface OnClickListener {
    fun onClick(storeEntity: StoreEntity){}
    fun onFavoriteStore(storeEntity: StoreEntity){}
    fun onDeleteStore(storeEntity: StoreEntity){}

}