package com.bilsem.mutfakdolabi.Objeler

import kotlinx.serialization.Serializable

@Serializable
data class Grup(val baslik: String, val uid: String,val isOwner: Boolean=false) :
    java.io.Serializable {

}