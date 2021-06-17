package com.bilsem.mutfakdolabi.helpers

object InputUtils {

    const val PRODUCT_NAME_MAX_LENGTH = 64
    const val PRODUCT_NAME_MIN_LENGTH = 3
    const val REGEX_ONLY_NON_SPECIAL_CHARACTERS = "[^a-z0-9 ]"

    fun IsValidEmail(email: String): Boolean {
        return true
    }

    fun isValidProductName(productName: String): Boolean {
        if (productName.length > PRODUCT_NAME_MAX_LENGTH || productName.length < PRODUCT_NAME_MIN_LENGTH) return false
        if (!productName.matches(Regex(REGEX_ONLY_NON_SPECIAL_CHARACTERS))) return false
        return true
    }
}