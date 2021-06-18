package com.bilsem.mutfakdolabi.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bilsem.mutfakdolabi.R
import com.bilsem.mutfakdolabi.adapters.RecyclerViewAdapterProduct
import com.bilsem.mutfakdolabi.objects.Product
import com.bilsem.mutfakdolabi.viewmodels.ProductAddViewModel
import kotlinx.android.synthetic.main.fragment_talep_ekle.view.*

class TalepEkleFragment : Fragment() {


    companion object {
        const val TAG = "TALEPEKLEFRAGMENT"
    }

    private val productList = arrayListOf<Product>()
    private val productViewModel: ProductAddViewModel by activityViewModels()
    private val recyclerViewAdapterProduct = RecyclerViewAdapterProduct(productList)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_talep_ekle, container, false)

        view.buttonFragmentTalepEkleAddProduct.setOnClickListener { button ->
            val fragment = ProductAddFragment()
            fragment.show(childFragmentManager, ProductAddFragment.TAG)
        }
        view.recyclerViewFragmentTalepEkleProductList.layoutManager =
            LinearLayoutManager(requireContext())
        view.recyclerViewFragmentTalepEkleProductList.adapter = recyclerViewAdapterProduct

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productViewModel.selectedItem.observe(viewLifecycleOwner, Observer { product ->
            productList.add(product)
            recyclerViewAdapterProduct.notifyItemInserted(productList.size)
        })
    }
}
