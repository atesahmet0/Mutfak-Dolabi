package com.bilsem.mutfakdolabi.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bilsem.mutfakdolabi.objects.Product

class ProductViewModel : ViewModel() {
    private val mutableSelectedProduct = MutableLiveData<Product>()
    val selectedItem: LiveData<Product> get() = mutableSelectedProduct

    fun setProduct(product: Product) {
        mutableSelectedProduct.value = product
    }

}