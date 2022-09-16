package com.example.appstoreunderstan

import com.example.appstoreunderstan.common.StoreEntity

interface OnClickListener {
    fun onClick(storeId: Long){}
    fun onFavoriteStore(storeEntity: StoreEntity){}
    fun onDeleteStore(storeEntity: StoreEntity){}

}