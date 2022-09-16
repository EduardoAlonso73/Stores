package com.example.appstoreunderstan.common.utils

import com.example.appstoreunderstan.common.entities.StoreEntity

interface MainAux {
    fun hideBtnFb(isVisible:Boolean=true)
    fun hideMenu( menuVisibility:Boolean)
    fun addStore(storeEntity: StoreEntity)
    fun updateStore(storeEntity: StoreEntity)

}