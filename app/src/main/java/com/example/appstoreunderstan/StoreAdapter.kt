package com.example.stores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appstoreunderstan.OnClickListener
import com.example.appstoreunderstan.R
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

      val store=nStores.get(position)
        with(holder){
            setListener(store)
            binding.tvName.text=store.name
            binding.cbFavorite.isChecked=store.isFavorite
        }
    }

    override fun getItemCount(): Int = nStores.size


    fun addStore(store: StoreEntity) {
       nStores.add(store)
        notifyDataSetChanged()
    }

    fun setListStore(storeList: MutableList<StoreEntity>) {
        this.nStores=storeList
        notifyDataSetChanged()
    }

    fun updateStore(storeEntity: StoreEntity) {
        val i = nStores.indexOf(storeEntity)
        if (i!=-1){
            nStores.set(i,storeEntity)
            notifyItemChanged(i)
        }
    }

    fun deleteStore(storeEntity: StoreEntity) {
        val i = nStores.indexOf(storeEntity)
        if (i!= -1){
            nStores.removeAt(i)
            notifyItemRemoved(i)
        }
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemStoreBinding.bind(view)
         fun setListener(storeEntity: StoreEntity) {
             with(binding.root){
             setOnClickListener { listener.onClick(storeEntity) }
             setOnLongClickListener { listener.onDeleteStore(storeEntity )
                   true }
             setOnClickListener {listener.onFavoriteStore(storeEntity) }
             }

        }
    }
}