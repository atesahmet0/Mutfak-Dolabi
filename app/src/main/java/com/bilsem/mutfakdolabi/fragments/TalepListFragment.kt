package com.bilsem.mutfakdolabi.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bilsem.mutfakdolabi.R
import com.bilsem.mutfakdolabi.adapters.RecyclerViewAdapterOrder
import com.bilsem.mutfakdolabi.objects.Grup
import com.bilsem.mutfakdolabi.objects.Order
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.android.synthetic.main.fragment_talep_list.view.*

class TalepListFragment : Fragment() {
    companion object {
        const val TAG = "TALEPLISTFRAGMENT"
    }

    private var groupsOfCurrentUser = arrayListOf<Grup>()
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val orderList = arrayListOf<Order>()
    private val adapterRecyclerViewAdapterOrder = RecyclerViewAdapterOrder(orderList)

    private val detachList = arrayListOf<ListenerRegistration>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_talep_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.recyclerViewOrderList.layoutManager = LinearLayoutManager(context)
        view.recyclerViewOrderList.adapter = adapterRecyclerViewAdapterOrder
        for (i in 1..5) {
            orderList.add(Order("Ev"))
            adapterRecyclerViewAdapterOrder.notifyItemInserted(i)
        }


    }

    override fun onStop() {
        super.onStop()
        detachList.forEach {
            it.remove()
        }
    }

}