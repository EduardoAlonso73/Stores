package com.example.appstoreunderstan.mainModule

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.appstoreunderstan.*
import com.example.appstoreunderstan.common.entities.StoreEntity
import com.example.appstoreunderstan.common.utils.TypeError
import com.example.appstoreunderstan.databinding.ActivityMainBinding
import com.example.appstoreunderstan.editModel.EditStoreFragment
import com.example.appstoreunderstan.editModel.viewModel.EditStoreViewModel
import com.example.appstoreunderstan.mainModule.adapter.OnClickListener
import com.example.appstoreunderstan.mainModule.adapter.StoreListAdapter
import com.example.appstoreunderstan.mainModule.viewModel.MainViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity(), OnClickListener {

    private lateinit var  mBinding: ActivityMainBinding
    private lateinit var  mAdapter: StoreListAdapter
    private lateinit var mGridLayout: GridLayoutManager
    //MVVM
    private  lateinit var mMainViewModel: MainViewModel
    private  lateinit var mEditStoreViewModel:EditStoreViewModel




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
        mBinding= ActivityMainBinding.inflate(layoutInflater )
        setContentView(mBinding.root)
        initRecycleView()
        mBinding.btnFa.setOnClickListener{ launchEditFragment() }
    }


    private  fun setupViewModel() {

        mMainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        mMainViewModel.getStores().observe(this) { store ->
            mBinding.Progress.visibility=View.GONE
            mAdapter.submitList(store)

        }

       mMainViewModel.isShowProgressBar().observe(this){
            mBinding.Progress.visibility= if(it)View.VISIBLE else View.GONE
        }
        mMainViewModel.getTypeError().observe(this){type ->
            val msgRes = when(type){
               TypeError.GET-> R.string.main_error_get
               TypeError.DELETE->R.string.main_error_delete
               TypeError.INSERT->R.string.main_error_insert
               TypeError.UPDATE->R.string.main_error_update
                else->R.string.main_error_unknown
            }
            Snackbar.make(mBinding.root,msgRes,Snackbar.LENGTH_SHORT).show()

        }
        mEditStoreViewModel = ViewModelProvider(this)[EditStoreViewModel::class.java]
        mEditStoreViewModel.getShowFab().observe(this) { isVisible ->
            if (isVisible) mBinding.btnFa.show() else mBinding.btnFa.hide()
        }



    }

    private fun launchEditFragment(storeEntity: StoreEntity= StoreEntity()){
        mEditStoreViewModel.setShowFab(false)
        mEditStoreViewModel.setStoreSelectored(storeEntity)
        println("StoreEntity ===> $storeEntity")
        val fragment= EditStoreFragment()
        val fragmentManager=supportFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.contreinerMain,fragment)
        fragmentTransaction.addToBackStack(null)// Me permite regresar al main activity
        fragmentTransaction.commit()


    }

    private  fun initRecycleView(){
        mAdapter= StoreListAdapter((this))
        mGridLayout=GridLayoutManager(this,2)
        mBinding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager=mGridLayout
            adapter=mAdapter
        }
    }


    /*==============================================
                interface  onChecklists
    ==============================================*/

    override fun onClick(storeEntity: StoreEntity) {
        launchEditFragment(storeEntity)
    }


    override fun onFavoriteStore(storeEntity: StoreEntity) {

        mMainViewModel.updateStoreFavorite(storeEntity) }

    override fun onDeleteStore(storeEntity: StoreEntity) {
        // val itemsOption= arrayOf("Eliminar","Llamar","Ir al sition web")
        val itemsOption= resources.getTextArray(R.array.array_optins_item)
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.dialog_delete_title)
            .setItems(itemsOption){ _, i ->
                when(i){
                    0->{confirmDelete(storeEntity)}
                    1->{dial(storeEntity.phone)}
                    2->{goToWebsite(storeEntity.website)}
                    else->{}
                }
            }
            .show()
    }

    private fun confirmDelete(storeEntity: StoreEntity){
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.dialog_delete_title)
            .setPositiveButton(R.string.dialog_delete_confirm){ _, _ ->
                mMainViewModel.deleteStore(storeEntity)
            }
            .setNegativeButton(R.string.dialog_delete_cancel,null)
            .show()
    }


    private fun dial(numberPhone:String){
        val callIntent=Intent().apply {
            action=Intent.ACTION_DIAL
            data= Uri.parse("tel:$numberPhone")
        }
        startIntent(callIntent)
    }

    private fun goToWebsite(website:String) {

        if (website.isEmpty()) {
            Toast.makeText(this, "No dispone de un stion web ", Toast.LENGTH_SHORT).show()
        } else {
            val websiteIntert = Intent().apply {
                action = Intent.ACTION_VIEW
                data = Uri.parse((website))
            }
            startIntent(websiteIntert)
        }
    }

    private fun startIntent(intent: Intent){
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(this, "No se encontro una app compatible", Toast.LENGTH_SHORT).show()
        }
    }


}

