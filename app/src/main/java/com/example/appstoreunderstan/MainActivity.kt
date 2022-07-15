package com.example.appstoreunderstan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.appstoreunderstan.databinding.ActivityMainBinding
import com.example.stores.OnClickListener
import com.example.stores.StoreAdapter
import com.example.stores.StoreEntity
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


private lateinit var  mBinding: ActivityMainBinding
private lateinit var  mAdapter:StoreAdapter
private lateinit var mGridLayout: GridLayoutManager

class MainActivity : AppCompatActivity(),OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding= ActivityMainBinding.inflate(layoutInflater )
        setContentView(mBinding.root)
        mBinding.btnSave.setOnClickListener {
            val store= StoreEntity(name=mBinding.etName.text.toString().trim())
         Thread{
             StoreApplication.database.storeDoa().addStore(store)
         }.start()
            mAdapter.addStore(store)
        }

        initRecycleView()
    }


    private  fun initRecycleView(){
        mAdapter= StoreAdapter(mutableListOf(),this)
        mGridLayout=GridLayoutManager(this,2)
        getListStore()
        mBinding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager=mGridLayout
            adapter=mAdapter
        }
    }


    private fun getListStore(){
        doAsync {
            val storeList = StoreApplication.database.storeDoa().getListAllStore()
            uiThread {mAdapter.setListStore(storeList)}
        }


    }
}