package com.example.appstoreunderstan

interface MainAux {
    fun hideBtnFb(isVisible:Boolean=true)
    fun hideMenu( menuVisibility:Boolean)
    fun addStore(storeEntity: StoreEntity)
    fun updateStore(storeEntity: StoreEntity)

}