package com.bilsem.mutfakdolabi.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bilsem.mutfakdolabi.R
import com.bilsem.mutfakdolabi.fragments.TalepEkleFragment
import com.bilsem.mutfakdolabi.fragments.ToplulukFragment

class AyarlarActivty : AppCompatActivity() {
    companion object {
        const val KEY_FRAGMENT_TO_PUT = "FRAGMENTTOPUT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ayarlar_activty)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        setFragment()

    }

    private fun setFragment() {
        val fragmentstr = intent.extras?.getString(KEY_FRAGMENT_TO_PUT)
        when (fragmentstr) {
            ToplulukFragment.TAG -> {
                setLayout(ToplulukFragment())
            }
            TalepEkleFragment.TAG -> {
                setLayout(TalepEkleFragment())
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