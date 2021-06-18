package com.bilsem.mutfakdolabi.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bilsem.mutfakdolabi.R
import com.bilsem.mutfakdolabi.objects.Product
import kotlinx.android.synthetic.main.recyclerview_product_item.view.*

class RecyclerViewAdapterProduct(val productList: List<Product>) :
    RecyclerView.Adapter<RecyclerViewAdapterProduct.RecyclerViewAdapterProductViewHolder>() {
    inner class RecyclerViewAdapterProductViewHolder(itemView: View) : RecyclerView.ViewHolder(
        itemView
    ) {
        fun bindView(product: Product) {
            itemView.textViewRecyclerViewProductItemTitle.text = product.title
            itemView.textViewRecyclerViewProductItemAmount.text =
                "${product.amountsToGet} ${product.measurementUnit}"
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewAdapterProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_product_item, parent, false)
        return RecyclerViewAdapterProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewAdapterProductViewHolder, position: Int) {
        holder.bindView(productList.get(position))
    }

    override fun getItemCount(): Int {
        return productList.size
    }

}