package com.bilsem.mutfakdolabi.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bilsem.mutfakdolabi.R
import kotlinx.android.synthetic.main.fragment_talep_ekle.view.*

class TalepEkleFragment : Fragment() {


    companion object {
        const val TAG = "TALEPEKLEFRAGMENT"
    }

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


}
