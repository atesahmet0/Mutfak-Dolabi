package com.bilsem.mutfakdolabi.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bilsem.mutfakdolabi.objects.Grup
import com.bilsem.mutfakdolabi.R
import kotlinx.android.synthetic.main.recyclerview_grup_item.view.*

class RecyclerViewAdapterGrup(val gruplar: List<Grup>) : RecyclerView.Adapter<RecyclerViewAdapterGrup.GrupViewHolder>() {
    lateinit var mGrupAdapterItemClickListener: grupAdapterItemClickListener;

    inner class GrupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bindView(grup: Grup){
            itemView.textViewRecyclerViewGrupBaslik.text=grup.baslik
            itemView.imageViewRecyclerViewGrupSettings.setOnClickListener { mGrupAdapterItemClickListener.onItemClick(itemView,adapterPosition,gruplar) }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GrupViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_grup_item, parent, false)
        return GrupViewHolder(view)
    }

    override fun onBindViewHolder(holder: GrupViewHolder, position: Int) {
        holder.bindView(gruplar.get(position))
    }

    override fun getItemCount(): Int {
        return gruplar.size
    }
    public interface grupAdapterItemClickListener{
        fun onItemClick(view: View,position: Int,dataList: List<Grup>);
    }

}
