package com.example.appstoreunderstan

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.appstoreunderstan.databinding.FragmentEditStoreBinding
import com.example.stores.StoreEntity
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class EditStoreFragment : Fragment() {
private lateinit var  mBinding: FragmentEditStoreBinding
private var mActivity :MainActivity? = null
    private var mIsEditMode:Boolean =false
    private  var mStoreEntity:StoreEntity?=null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        mBinding= FragmentEditStoreBinding.inflate(inflater,container,false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getLong(getString(R.string.arg_id),0)
        if(id != null && id !=0L ) {
            mIsEditMode=true
            getStore(id)
        }
        else{
                mIsEditMode=false
            mStoreEntity= StoreEntity(name = "", phone = "", photoUrl = "")
        }

        mActivity = activity as? MainActivity
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)// Habilitamos el icono de retroceso <-
        mActivity?.supportActionBar?.title=getString(R.string.edit_store_add)
        setHasOptionsMenu(true) //Habilitamos la opcion  para agregar  actiones en el appBar

        mBinding.etPhotoUrl.addTextChangedListener {
            Glide.with(this)
                .load(mBinding.etPhotoUrl.text.toString())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(mBinding.imgPhoto)
        }
                    with(mBinding){
                        etName.addTextChangedListener{validateFields(mBinding.tilName)}
                        etPhone.addTextChangedListener{validateFields(mBinding.tilPhone)}
                        etPhotoUrl.addTextChangedListener{validateFields(mBinding.tiPhotoUrl)}
                    }


    }
    private fun getStore(id: Long) {
        doAsync {
            mStoreEntity=StoreApplication.database.storeDoa().getStoreById(id)
            uiThread {
                mStoreEntity.let { setUiStore(mStoreEntity!!) }

            }
        }
    }

    private fun setUiStore(mStoreEntity: StoreEntity) {
        with(mBinding) {
            //Usamos setText para las propiedades edit text ya que su propiedada text no permite como un inputText
            etName.setText(mStoreEntity.name)
            etPhone.text=mStoreEntity.phone.editable()//Otra forma de hacerlo
            etWebsite.text=mStoreEntity.website.editable()
            etPhotoUrl.text=mStoreEntity.photoUrl.editable()
        }
    }

    private fun String.editable():Editable=Editable.Factory.getInstance().newEditable(this)


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
              if(mStoreEntity!=null && validateFields(mBinding.tiPhotoUrl,mBinding.tilPhone,mBinding.tilName)){
                  with(mStoreEntity!!){
                      name= mBinding.etName.text.toString().trim()
                      phone=mBinding.etPhone.text.toString().trim()
                      website=mBinding.etWebsite.text.toString().trim()
                      photoUrl=mBinding.etPhotoUrl.text.toString().trim()
                  }
                  // val store=StoreEntity(name=nameStore,phone=phone, website = website, photoUrl = photoUrl)
                  doAsync {
                      if (mIsEditMode)StoreApplication.database.storeDoa().updateStores(mStoreEntity!!)
                      else mStoreEntity!!.id=StoreApplication.database.storeDoa().addStore(mStoreEntity!!)

                      uiThread {
                          if(mIsEditMode){
                              mActivity?.updateStore(mStoreEntity!!)
                              Toast.makeText(context,"Tienda actulizada ",Toast.LENGTH_SHORT).show()
                          }else {
                              mActivity?.addStore(mStoreEntity!!)
                              Toast.makeText(context,"Tienda agregado", Toast.LENGTH_SHORT).show()
                          }
                          hideKeyboard()
                          mActivity?.onBackPressed()
                      }
                  }
              }
                true
            }else->{
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun validateFields(vararg textFiels:TextInputLayout): Boolean {
        var isValid = true
        for (textField in textFiels){
            if(textField.editText?.text.toString().trim().isEmpty()){
                textField.error=getString(R.string.helper_required)
                isValid=false
            }else textField.error=null
        }
        if (!isValid) Snackbar.make(mBinding.root,"Revise los campos requeridos",Snackbar.LENGTH_SHORT).show()
        return isValid
    }
/*    private fun validateFields(): Boolean {
        var isValid=true
        if(mBinding.etPhotoUrl.text.toString().trim().isEmpty()){
            mBinding.tiPhotoUrl.error=getString(R.string.helper_required)
            mBinding.etPhotoUrl.requestFocus()
            isValid=false
        }

        if(mBinding.etPhone.text.toString().trim().isEmpty()){
            mBinding.tilPhone.error=getString(R.string.helper_required)
            mBinding.etPhone.requestFocus()
            isValid=false
        }

        if(mBinding.etName.text.toString().trim().isEmpty()){
            mBinding.tilName.error=getString(R.string.helper_required)
            mBinding.etName.requestFocus()
            isValid=false
        }
        return  isValid
    }*/

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