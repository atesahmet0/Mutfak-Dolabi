package com.bilsem.mutfakdolabi.activities

import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bilsem.mutfakdolabi.R
import com.bilsem.mutfakdolabi.helpers.DatabaseHelper
import com.bilsem.mutfakdolabi.objects.Kisi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    val ERROR_EMAIL_MAKS_KARAKTER="En fazla 320 karakter"
    val ERROR_EMAIL_GECERSIZ="Gecerli bir eposta giriniz"

    val ERROR_SIFRE_MIN_KARAKTER="En az 8 karakter"
    val ERROR_SIFRE_MAKS_KARAKTER="En fazla 64 karakter"

    val ERROR_KULLANICIADI_MIN_KARAKTER="En az 8 karakter"
    val ERROR_KULLANICIADI_MAX_KARAKTER="En fazla 30 karakter"

    val EPOSTA_ONAY="E-posta adresinizi onaylayin"
    lateinit var mAuth : FirebaseAuth
    lateinit var mDatabase : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        mAuth = FirebaseAuth.getInstance()
        mDatabase= FirebaseFirestore.getInstance()


        textViewGirisYap.setOnClickListener {
            finish()
        }
        buttonKayitOl.setOnClickListener {

            buttonKayitOl.isClickable=false

            textInputLayoutSifreRegister.error=null
            textInputKullaniciAdiRegister.error=null
            textInputLayoutEpostaRegister.error=null

            var hataVarmi=false
            if (!Patterns.EMAIL_ADDRESS.matcher(textInputLayoutEpostaRegister.editText!!.text.toString()).matches()) {textInputLayoutEpostaRegister.error=ERROR_EMAIL_GECERSIZ
            hataVarmi=true}
            if (textInputLayoutSifreRegister.editText!!.text!!.trim().length < 8){textInputLayoutSifreRegister.error=ERROR_SIFRE_MIN_KARAKTER
            hataVarmi=true}
            if(textInputKullaniciAdiRegister.editText!!.text!!.trim().length<8){textInputKullaniciAdiRegister.error=(ERROR_KULLANICIADI_MIN_KARAKTER)
            hataVarmi=true}

            if (!hataVarmi){
                val eposta = textInputLayoutEpostaRegister.editText!!.text.toString()
                val sifre = textInputLayoutSifreRegister.editText!!.text.toString()
                val username= textInputKullaniciAdiRegister.editText!!.text.toString()
                kayitOl(Kisi(username,eposta),sifre)
            }

            buttonKayitOl.isClickable=true

        }
    }

    fun kayitOl(kisi: Kisi, sifre: String){
        mAuth.createUserWithEmailAndPassword(kisi.eposta,sifre).addOnCompleteListener {
            if(it.isSuccessful){
                Toast.makeText(this,EPOSTA_ONAY,Toast.LENGTH_SHORT).show()
                mDatabase.collection(DatabaseHelper.USERS).document(mAuth.currentUser.uid).set(mapOf(DatabaseHelper.USER_USERNAME to kisi.kullaniciadi,DatabaseHelper.USER_EMAIL to kisi.eposta))
                    .addOnSuccessListener {
                        mailGonder()
                        mAuth.signOut()
                        finish()
                    }
            }else{
                Toast.makeText(this,it.exception.toString(),Toast.LENGTH_SHORT).show()
            }
        }
        }

    private fun mailGonder() {
        var kullanici = mAuth.currentUser
        if(kullanici!=null)(
                kullanici.sendEmailVerification().addOnCompleteListener {

                }
        )
    }
}
    
