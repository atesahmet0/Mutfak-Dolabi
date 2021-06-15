package com.bilsem.mutfakdolabi.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.bilsem.mutfakdolabi.fragments.ToplulukFragment
import com.bilsem.mutfakdolabi.R

class AyarlarActivty : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ayarlar_activty)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        setFragment()

    }

    private fun setFragment() {
        val fragmentstr= intent.extras?.get(MainActivity.AYARLAR_FRAGMENT)
        when(fragmentstr){
            MainActivity.AYARLAR_FRAGMENT_TOPLULUK -> {
                setLayout(ToplulukFragment())
            }
        }
    }

    private fun setLayout(fragment: Fragment){
        supportFragmentManager.beginTransaction().add(R.id.frameLayoutAyarlar,fragment).commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

}