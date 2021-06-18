package com.bilsem.mutfakdolabi.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.bilsem.mutfakdolabi.R
import com.bilsem.mutfakdolabi.objects.Product
import com.bilsem.mutfakdolabi.viewmodels.ProductAddViewModel
import kotlinx.android.synthetic.main.fragment_talep_ekle.view.*

class TalepEkleFragment : Fragment() {


    companion object {
        const val TAG = "TALEPEKLEFRAGMENT"
    }

    val productList = arrayListOf<Product>()
    private val productViewModel: ProductAddViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_talep_ekle, container, false)

        view.buttonFragmentTalepEkleAddProduct.setOnClickListener { button ->
            val fragment = ProductAddFragment()
            fragment.show(childFragmentManager, ProductAddFragment.TAG)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productViewModel.selectedItem.observe(viewLifecycleOwner, Observer { product ->

        })
    }
}
