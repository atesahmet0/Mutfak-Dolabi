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
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.bilsem.mutfakdolabi.R
import com.bilsem.mutfakdolabi.helpers.InputUtils
import com.bilsem.mutfakdolabi.objects.Product
import com.bilsem.mutfakdolabi.viewmodels.ProductAddViewModel
import kotlinx.android.synthetic.main.fragment_product_add.view.*

class ProductAddFragment : DialogFragment() {
    companion object {
        const val TAG = "PRODUCTADDDIALOGFRAGMENT"
    }

    val productViewModel: ProductAddViewModel by activityViewModels()

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

        val measurementUnits = Product.MeasurementUnit.values()

        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, measurementUnits)

        (view.exposedDropdownMenuFragmentProductAddMeasurementUnit.editText as? AutoCompleteTextView)?.setAdapter(
            adapter
        )

        view.buttonFragmentProductAddCancel.setOnClickListener { dialog?.dismiss() }
        view.buttonFragmentProductAddDone.setOnClickListener {
            val productName = view.textInputLayoutFragmentProductAddTitle.editText?.text
            val productAmount = view.textInputLayoutFragmentProductAddAmount.editText?.text
            val measurementUnit =
                view.exposedDropdownMenuFragmentProductAddMeasurementUnit.editText?.text

            var error = false
            if (productName == null || productName.length < InputUtils.PRODUCT_NAME_MIN_LENGTH) {
                view.textInputLayoutFragmentProductAddTitle.error =
                    "En az ${InputUtils.PRODUCT_NAME_MIN_LENGTH} karakter giriniz"
                error = true
            }
            if (productAmount == null || productAmount.isBlank()) {
                view.textInputLayoutFragmentProductAddAmount.error = "Miktar giriniz"
                error = true
            }
            if (measurementUnit == null) {
                view.exposedDropdownMenuFragmentProductAddMeasurementUnit.error =
                    "Bir birim seciniz"
                error = true
            }
            implementErrorOnViews(view)

            if (error) return@setOnClickListener

            productViewModel.setProduct(
                Product(
                    productName.toString(),
                    productAmount.toString().toInt(),
                    measurementUnit = Product.MeasurementUnit.fromString(measurementUnit.toString())!!
                )
            )
            dialog!!.dismiss()
        }
        return view
    }

    private fun implementErrorOnViews(view: View) {
        view.textInputLayoutFragmentProductAddAmount.editText?.addTextChangedListener {
            if (it != null && !it.toString().isBlank() && it.toString().toInt() > 0) {
                view.textInputLayoutFragmentProductAddAmount.error = null
            }
        }
        view.textInputLayoutFragmentProductAddTitle.editText?.addTextChangedListener {
            if (it != null && it.length > InputUtils.PRODUCT_NAME_MIN_LENGTH) {
                view.textInputLayoutFragmentProductAddTitle.error = null
            }
        }
        view.exposedDropdownMenuFragmentProductAddMeasurementUnit.editText?.addTextChangedListener {
            if (it != null) {
                view.exposedDropdownMenuFragmentProductAddMeasurementUnit.error = null
            }
        }
    }


}