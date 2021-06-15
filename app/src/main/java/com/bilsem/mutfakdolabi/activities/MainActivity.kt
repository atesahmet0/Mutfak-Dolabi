package com.bilsem.mutfakdolabi.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.bilsem.mutfakdolabi.fragments.TalepListFragment
import com.bilsem.mutfakdolabi.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    companion object{
        const val AYARLAR_FRAGMENT="fragment"
        const val AYARLAR_FRAGMENT_TOPLULUK="topluluk"
    }

    lateinit var mauth: FirebaseAuth

    lateinit var db :FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mauth= FirebaseAuth.getInstance()
        if (mauth==null){
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
        db = FirebaseFirestore.getInstance()

        supportFragmentManager.beginTransaction().add(R.id.frameLayoutMainActivity,TalepListFragment()).commitNow()

    }
    fun cikisYap(){
        mauth.signOut()
        startActivity(Intent(this,LoginActivity::class.java))
        finish()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.actionbar_main_items,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_topluluk ->{
                intent = Intent(this,AyarlarActivty::class.java)
                intent.putExtra(AYARLAR_FRAGMENT, AYARLAR_FRAGMENT_TOPLULUK)
                startActivity(intent)
            }
            R.id.action_cikis ->{
                cikisYap()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}