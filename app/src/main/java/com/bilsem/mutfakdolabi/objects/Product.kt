package com.bilsem.mutfakdolabi.objects

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val title: String,
    val amountsToGet: Int,
    val currentAmounts: Int = 0,
    val measurementUnit: measurementUnit
)

enum class measurementUnit {
    GRAMS {
        override fun toString(): String {
            return "Gram"
        }
    },
    LITERS {
        override fun toString(): String {
            return "Litre"
        }
    },
    PIECE {
        override fun toString(): String {
            return "Adet"
        }
    }
}
