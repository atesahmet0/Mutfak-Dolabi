package com.bilsem.mutfakdolabi.fragments

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.bilsem.mutfakdolabi.R
import com.bilsem.mutfakdolabi.viewmodels.ProductViewModel
import kotlinx.android.synthetic.main.fragment_product_add.view.*

class ProductAddFragment : DialogFragment() {
    companion object {
        const val TAG = "PRODUCTADDDIALOGFRAGMENT"
    }

    val productViewModel: ProductViewModel by activityViewModels()

    override fun onStart() {
        super.onStart()

        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        val insetDrawable = InsetDrawable(ColorDrawable(Color.TRANSPARENT), 20)
        dialog?.window?.setBackgroundDrawable(insetDrawable)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_product_add, container, false)
        val items = listOf("Adet", "Kg", "Litre", "Gram")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        (view.exposedDropdownMenuFragmentProductAddMeasurementUnit.editText as? AutoCompleteTextView)?.setAdapter(
            adapter
        )
        view.buttonFragmentProductAddCancel.setOnClickListener { dialog?.dismiss() }
        return view
    }

}