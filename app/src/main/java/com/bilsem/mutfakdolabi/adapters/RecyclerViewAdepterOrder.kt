package com.bilsem.mutfakdolabi.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bilsem.mutfakdolabi.objects.Order

class RecyclerViewAdepterOrder(ordersList: List<Order>) :
    RecyclerView.Adapter<RecyclerViewAdepterOrder.RecyclerViewAdapterOrderViewHolder>() {

    inner class RecyclerViewAdapterOrderViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewAdapterOrderViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerViewAdapterOrderViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}
