package com.bilsem.mutfakdolabi.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bilsem.mutfakdolabi.R

class TalepEkleFragment : Fragment() {


    companion object {
        const val TAG = "TALEPEKLEFRAGMENT"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_talep_ekle, container, false)
    }

}
