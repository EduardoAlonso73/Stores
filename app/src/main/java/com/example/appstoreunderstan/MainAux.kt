package com.example.appstoreunderstan

import com.example.stores.StoreEntity

interface MainAux {
    fun hideBtnFb(isVisible:Boolean=false)
    fun addStore(storeEntity: StoreEntity)
    fun updateStore(storeEntity: StoreEntity)

}