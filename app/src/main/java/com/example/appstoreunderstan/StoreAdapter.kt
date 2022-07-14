package com.example.stores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appstoreunderstan.R
import com.example.appstoreunderstan.databinding.ItemStoreBinding


class StoreAdapter(private var nStores:MutableList<Store>, private  var listener:OnClickListener):
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
        }
    }



    override fun getItemCount(): Int = nStores.size


    fun addStore(store: Store) {
       nStores.add(store)
        notifyDataSetChanged()
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemStoreBinding.bind(view)
         fun setListener(store: Store) {
            binding.root.setOnClickListener {
            listener.onClick(store)
}
        }
    }
}