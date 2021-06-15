package com.bilsem.mutfakdolabi.objects

import kotlinx.serialization.Serializable

@Serializable
data class Order(
    val title: String,
    val comment: String,
    val userWhoPublished: Kisi,
    val listOfProductsToGet: List<Product>
)