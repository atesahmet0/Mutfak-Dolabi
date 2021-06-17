package com.bilsem.mutfakdolabi.activities

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bilsem.mutfakdolabi.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    lateinit var mauth: FirebaseAuth
    lateinit var mainActivityIntent: Intent
    lateinit var registerActivityIntent: Intent
    val ERROR_GECERSIZ_ADRESLER = "Eposta veya Sifre hatali"
    val ERROR_EPOSTA_ONAYSIZ="Lutfen eposta adresinizi onaylayiniz"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //lateinit vars
        mauth= FirebaseAuth.getInstance()
        mainActivityIntent=Intent(this,MainActivity::class.java)
        registerActivityIntent=Intent(this,RegisterActivity::class.java)
        if(mauth.currentUser!=null) {
            startActivity(mainActivityIntent)
            finish()
        }

        textViewKayitOl.setOnClickListener {
            startActivity(registerActivityIntent)

        }
        buttonGiris.setOnClickListener {
            buttonGiris.isClickable=false
            if (textInputKullaniciAdiLogin.editText!!.text!=null && textInputSifreLogin.editText!!.text!=null){
                if (Patterns.EMAIL_ADDRESS.matcher(textInputKullaniciAdiLogin.editText!!.text.toString()).matches()){
                    mauth.signInWithEmailAndPassword(
                        textInputKullaniciAdiLogin.editText!!.text.toString(),
                        textInputSifreLogin.editText!!.text.toString()).addOnCompleteListener {
                        if (it.isSuccessful){
                            if (mauth.currentUser.isEmailVerified){
                                startActivity(mainActivityIntent)
                                finish()
                            }else{
                                Toast.makeText(this,ERROR_EPOSTA_ONAYSIZ,Toast.LENGTH_SHORT).show()
                                mauth.signOut()
                            }

                        }else{
                            Toast.makeText(this,it.exception.toString(),Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{
                    hataliBilgilerMesaj()
                }

            }else{
                hataliBilgilerMesaj()
            }
            buttonGiris.isClickable=true
        }
    }

    override fun onStart() {
        super.onStart()
        FirebaseAuth.getInstance().addAuthStateListener { initAuthStateListener() }
    }

    override fun onStop() {
        super.onStop()
        FirebaseAuth.getInstance().removeAuthStateListener { initAuthStateListener() }
    }
    private fun initAuthStateListener() = FirebaseAuth.AuthStateListener{
        if (it.currentUser!=null){
            if (!it.currentUser.isEmailVerified){
                Toast.makeText(this,ERROR_EPOSTA_ONAYSIZ,Toast.LENGTH_SHORT).show()
                FirebaseAuth.getInstance().signOut()
            }
        }

    }
    private fun hataliBilgilerMesaj(){
        Toast.makeText(this,ERROR_GECERSIZ_ADRESLER,Toast.LENGTH_SHORT).show()
    }
}