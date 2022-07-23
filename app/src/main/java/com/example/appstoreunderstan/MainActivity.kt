package com.example.appstoreunderstan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.appstoreunderstan.databinding.ActivityMainBinding
import com.example.stores.StoreAdapter
import com.example.stores.StoreEntity
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread




class MainActivity : AppCompatActivity(), OnClickListener,MainAux {

    private lateinit var  mBinding: ActivityMainBinding
    private lateinit var  mAdapter:StoreAdapter
    private lateinit var mGridLayout: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding= ActivityMainBinding.inflate(layoutInflater )
        setContentView(mBinding.root)

        initRecycleView()
        mBinding.btnFa.setOnClickListener{ launchEditFragment() }
    }
        private fun launchEditFragment(args:Bundle?=null){
            val fragment=EditStoreFragment()

            if(args!=null){fragment.arguments=args}

            val fragmentManager=supportFragmentManager
            val fragmentTransaction=fragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.contreinerMain,fragment)
            fragmentTransaction.addToBackStack(null)// Me permite regresar al main activity
            fragmentTransaction.commit()
            hideBtnFb(false)

        }

    private  fun initRecycleView(layout:Int = 2){
        mAdapter= StoreAdapter(mutableListOf(),this)
        mGridLayout=GridLayoutManager(this,layout)
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


    /* ==============================================
                          ACTION BAR
     =============================================== */

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_layout, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_grid2 -> {
                initRecycleView()
            }
            R.id.action_row -> { initRecycleView(1) }
        }
        return super.onOptionsItemSelected(item)
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
        doAsync {
            StoreApplication.database.storeDoa().updateStores(storeEntity)
            uiThread {
                Toast.makeText(this@MainActivity,storeEntity.isFavorite.toString(), Toast.LENGTH_SHORT).show()
                updateStore(storeEntity)
            }
        }
    }
    override fun onDeleteStore(storeEntity: StoreEntity) {
        doAsync {
            StoreApplication.database.storeDoa().deleteStore(storeEntity)
            uiThread {
                mAdapter.deleteStore(storeEntity)
            }
        }
    }
    /* ==============================================
                 interface  MainAux
     ============================================== */
    override fun hideBtnFb(isVisible: Boolean) {
        if(isVisible) mBinding.btnFa.show() else mBinding.btnFa.hide()
    }

    override fun addStore(storeEntity: StoreEntity) {
        mAdapter.addStore(storeEntity)
    }

    override fun updateStore(storeEntity: StoreEntity) {
    mAdapter.updateStore(storeEntity)
    }




}