package com.bilsem.mutfakdolabi.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bilsem.mutfakdolabi.R
import com.bilsem.mutfakdolabi.objects.Order
import kotlinx.android.synthetic.main.recyclerview_order_title.view.*

class RecyclerViewAdapterOrder(val ordersList: List<Order>) :
    RecyclerView.Adapter<RecyclerViewAdapterOrder.RecyclerViewAdapterOrderViewHolder>() {

    inner class RecyclerViewAdapterOrderViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        fun bindView(order: Order) {
            itemView.textViewRecyclerViewOrderTitleTitleOfGroup.text = order.title
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewAdapterOrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_order_title, parent, false)
        return RecyclerViewAdapterOrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewAdapterOrderViewHolder, position: Int) {
        holder.bindView(ordersList.get(position))
    }

    override fun getItemCount(): Int {
        return ordersList.size
    }
}
