package com.example.appstoreunderstan

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.appstoreunderstan.databinding.FragmentEditStoreBinding
import com.example.stores.StoreEntity
import com.google.android.material.snackbar.Snackbar
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class EditStoreFragment : Fragment() {
private lateinit var  mBinding: FragmentEditStoreBinding
private var mActivity :MainActivity? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        mBinding= FragmentEditStoreBinding.inflate(inflater,container,false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         mActivity = activity as? MainActivity
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)// Habilitamos el icono de retroceso <-
        mActivity?.supportActionBar?.title=getString(R.string.edit_store_add)
        setHasOptionsMenu(true) //Habilitamos la opcion  para agregar  actiones en el appBar
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_save,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            android.R.id.home->{
                    hideKeyboard()
                    mActivity?.onBackPressed()
                true
            }
            R.id.action_save-> {
                val nameStore= mBinding.etName.text.toString().trim()
                val phone=mBinding.etPhone.text.toString().trim()
                val website=mBinding.etWebsite.text.toString().trim()
                val store=StoreEntity(name=nameStore,phone=phone, website = website)
                doAsync {
                    store.id=StoreApplication.database.storeDoa().addStore(store)
                    uiThread {
                        mActivity?.addStore(store)
                        hideKeyboard()
                        Toast.makeText(context,"Tienda agregado", Toast.LENGTH_SHORT).show()
                        mActivity?.onBackPressed()
                    }
                }
                true
            }else->{
                super.onOptionsItemSelected(item)
            }
        }
    }


    private  fun hideKeyboard(){
        val imm =mActivity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken,0)
        //  imm.hideSoftInputFromWindow(view?.windowToken,0)    // Otro forma de hacerlo

    }

    override fun onDestroyView() // Metodo que se ejecuta antes del OnDistroy y es cuando se disvicula la vista
    {
        hideKeyboard()
        super.onDestroyView()
    }

    override fun onDestroy() {
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        mActivity?.supportActionBar?.title=getString(R.string.app_name)
        mActivity?.hideBtnFb(true)
        setHasOptionsMenu(false)
        super.onDestroy()


    }
}