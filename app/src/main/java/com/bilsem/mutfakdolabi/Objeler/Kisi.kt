package com.bilsem.mutfakdolabi.Objeler

import kotlinx.serialization.Serializable


@Serializable
data class Kisi(val kullaniciadi:String, val eposta: String, val groupsMemberOf: List<Grup> = listOfNotNull(), val uid :String="") :
    java.io.Serializable{

}