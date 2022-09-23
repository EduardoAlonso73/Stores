package com.example.appstoreunderstan.mainModule.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.appstoreunderstan.R
import com.example.appstoreunderstan.common.entities.StoreEntity
import com.example.appstoreunderstan.databinding.ItemStoreBinding


class StoreAdapter(private var nStores:MutableList<StoreEntity>, private  var listener: OnClickListener):
    RecyclerView.Adapter<StoreAdapter.ViewHolder>() {

    private  lateinit var  mContext:Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext=parent.context
        val view =LayoutInflater.from(mContext).inflate(R.layout.item_store,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val store= nStores[position]
        with(holder){
            setListener(store)
            binding.tvName.text=store.name
            binding.cbFavorite.isChecked=store.isFavorite
            Glide.with(mContext)
                .load(store.photoUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(binding.imgPhoto)
        }
    }
    override fun getItemCount(): Int = nStores.size


    fun setListStore(storeList: List<StoreEntity>) {
        //Realizamos un casteo list as MutableList
        this.nStores=storeList as MutableList<StoreEntity>
        notifyDataSetChanged()
    }

    fun addStore(store: StoreEntity) {
        if (store.id !=0L) {
            if(!nStores.contains(store)){
                nStores.add(store)
                notifyDataSetChanged()
               // notifyItemChanged(nStores.size-1)
            }else{
                updateStore(store)
            }
        }
    }

    private  fun updateStore(storeEntity: StoreEntity) {
        val i = nStores.indexOf(storeEntity)
        if (i != -1){
            nStores[i] = storeEntity
            notifyItemChanged(i)
        }
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemStoreBinding.bind(view)

        fun setListener(storeEntity: StoreEntity) {
            with(binding.root){
                setOnClickListener { listener.onClick(storeEntity) }
                setOnLongClickListener { listener.onDeleteStore(storeEntity )
                    true
                }
            }
            binding.cbFavorite.setOnClickListener{ listener.onFavoriteStore(storeEntity) }
        }
    }
}