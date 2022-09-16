package com.example.appstoreunderstan.common

import com.example.appstoreunderstan.common.StoreEntity

interface MainAux {
    fun hideBtnFb(isVisible:Boolean=true)
    fun hideMenu( menuVisibility:Boolean)
    fun addStore(storeEntity: StoreEntity)
    fun updateStore(storeEntity: StoreEntity)

}