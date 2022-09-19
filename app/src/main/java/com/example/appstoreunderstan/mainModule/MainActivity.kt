package com.example.appstoreunderstan.mainModule

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.appstoreunderstan.*
import com.example.appstoreunderstan.common.entities.StoreEntity
import com.example.appstoreunderstan.databinding.ActivityMainBinding
import com.example.appstoreunderstan.editModel.EditStoreFragment
import com.example.appstoreunderstan.editModel.viewModel.EditStoreViewModel
import com.example.appstoreunderstan.mainModule.adapter.OnClickListener
import com.example.appstoreunderstan.mainModule.adapter.StoreAdapter
import com.example.appstoreunderstan.mainModule.viewModel.MainViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder





class MainActivity : AppCompatActivity(), OnClickListener {

    private lateinit var  mBinding: ActivityMainBinding
    private lateinit var  mAdapter: StoreAdapter
    private lateinit var mGridLayout: GridLayoutManager
    //MVVM
    private  lateinit var mMainViewModel: MainViewModel
    private  lateinit var mEditStoreViewModel:EditStoreViewModel
    //



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
        mMainViewModel.getStores().observe(this) { store -> mAdapter.setListStore(store) }

        mEditStoreViewModel = ViewModelProvider(this)[EditStoreViewModel::class.java]
        mEditStoreViewModel.getShowFab().observe(this) { isVisible ->
            if (isVisible) mBinding.btnFa.show() else mBinding.btnFa.hide()
        }

    }

    private fun launchEditFragment(args:Bundle?=null){
            mEditStoreViewModel.setShowFab(false)
            val fragment= EditStoreFragment()
            if(args!=null){fragment.arguments=args}
            val fragmentManager=supportFragmentManager
            val fragmentTransaction=fragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.contreinerMain,fragment)
            fragmentTransaction.addToBackStack(null)// Me permite regresar al main activity
            fragmentTransaction.commit()
            //hideBtnFb(false)

        }

    private  fun initRecycleView(){
        mAdapter= StoreAdapter(mutableListOf(),this)
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

    override fun onClick(storeId: Long) {
     val args =Bundle()
        args.putLong(getString(R.string.arg_id),storeId)
        launchEditFragment(args)
    }


    override fun onFavoriteStore(storeEntity: StoreEntity) {
        storeEntity.isFavorite = !storeEntity.isFavorite
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

