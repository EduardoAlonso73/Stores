package com.example.appstoreunderstan.editModel

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.appstoreunderstan.R
import com.example.appstoreunderstan.common.entities.StoreEntity
import com.example.appstoreunderstan.databinding.FragmentEditStoreBinding
import com.example.appstoreunderstan.editModel.viewModel.EditStoreViewModel
import com.example.appstoreunderstan.mainModule.MainActivity
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout

class EditStoreFragment : Fragment() {
    private lateinit var  mBinding: FragmentEditStoreBinding
    //MVVM
    private  lateinit var  mEditStoreViewModel: EditStoreViewModel
    //
    private var mActivity : MainActivity? = null
    private var mIsEditMode:Boolean =false
    private  lateinit  var mStoreEntity: StoreEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mEditStoreViewModel= ViewModelProvider(requireActivity())[EditStoreViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mBinding= FragmentEditStoreBinding.inflate(inflater,container,false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupTextFields()
    }

    private fun setupViewModel() {
        mEditStoreViewModel.getStoreSelect().observe(viewLifecycleOwner){
            println("IT ===> $it")
            mStoreEntity=it?:StoreEntity()
            if(it !=null) {
                mIsEditMode=true
                setUiStore(it)
            } else{ mIsEditMode=false }
            setupActionBar()
        }

        mEditStoreViewModel.getResult().observe(viewLifecycleOwner){result->
            hideKeyboard()

            when(result){
                is Long ->{
                    mStoreEntity.id=result
                    mEditStoreViewModel.setStoreSelectored(mStoreEntity)
                    Toast.makeText(context,"Tienda agregado", Toast.LENGTH_SHORT).show()
                    mActivity?.onBackPressed()
                }
                is StoreEntity ->{
                    mEditStoreViewModel.setStoreSelectored(mStoreEntity)
                    Toast.makeText(context,"Tienda actulizada ",Toast.LENGTH_SHORT).show()
                    mActivity?.onBackPressed()
                }
            }



        }

    }



    private fun setupActionBar() {
        mActivity = activity as? MainActivity
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)// Habilitamos el icono de retroceso <-
        mActivity?.supportActionBar?.title=if(mIsEditMode) getString(R.string.edit_store) else getString(
            R.string.edit_store_add
        )
        setHasOptionsMenu(true) //Habilitamos la opcion  para agregar  actiones en el appBar
    }

    private fun setupTextFields() {
        with(mBinding){
            etName.addTextChangedListener{validateFields(mBinding.tilName)}
            etPhone.addTextChangedListener{validateFields(mBinding.tilPhone)}
            etPhotoUrl.addTextChangedListener{
                validateFields(mBinding.tiPhotoUrl)
                loadImage(it.toString())
            }
        }
    }
    private fun loadImage(url:String){
        Glide.with(this)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .into(mBinding.imgPhoto)
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
        // mActivity?.hideMenu(false)
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
            R.id.action_save -> {
                if(validateFields(mBinding.tiPhotoUrl,mBinding.tilPhone,mBinding.tilName)){
                    with(mStoreEntity){
                        name= mBinding.etName.text.toString().trim()
                        phone=mBinding.etPhone.text.toString().trim()
                        website=mBinding.etWebsite.text.toString().trim()
                        photoUrl=mBinding.etPhotoUrl.text.toString().trim()
                    }

                    if (mIsEditMode) mEditStoreViewModel.updateStore(mStoreEntity)
                    else mEditStoreViewModel.saveStore(mStoreEntity)
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

    private  fun hideKeyboard(){
        val imm =mActivity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(requireView().windowToken,0)
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
        mEditStoreViewModel.setShowFab(true)
        mEditStoreViewModel.setResult(Any())
        setHasOptionsMenu(false)
        super.onDestroy()


    }
}