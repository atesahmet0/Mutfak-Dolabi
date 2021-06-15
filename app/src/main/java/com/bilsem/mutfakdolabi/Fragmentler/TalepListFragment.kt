package com.bilsem.mutfakdolabi.Fragmentler

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bilsem.mutfakdolabi.Adapterler.RecyclerViewAdapterGrup
import com.bilsem.mutfakdolabi.Objeler.Grup
import com.bilsem.mutfakdolabi.Objeler.Kisi
import com.bilsem.mutfakdolabi.R
import com.bilsem.mutfakdolabi.helper.DatabaseHelper
import com.bilsem.mutfakdolabi.repository.FirestoreRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.android.synthetic.main.fragment_talep_list.view.*

class TalepListFragment : Fragment() {
    companion object{
        const val TAG = "TALEPLISTFRAGMENT"
    }

    private val firestoreRepository = FirestoreRepository
    private var groupsOfCurrentUser = arrayListOf<Grup>()
    private val firebaseAuth = FirebaseAuth.getInstance()
    val adapter = RecyclerViewAdapterGrup(groupsOfCurrentUser)

    private val detachList = arrayListOf<ListenerRegistration>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_talep_list,container,false)
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.recyclerViewTalepListesi.layoutManager=LinearLayoutManager(context)
        view.recyclerViewTalepListesi.adapter = adapter


    }

    override fun onStop() {
        super.onStop()
        detachList.forEach {
            it.remove()
        }
    }

}