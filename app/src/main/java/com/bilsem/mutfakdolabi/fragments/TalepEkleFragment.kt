package com.bilsem.mutfakdolabi.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bilsem.mutfakdolabi.R
import com.bilsem.mutfakdolabi.adapters.RecyclerViewAdapterProduct
import com.bilsem.mutfakdolabi.helpers.DatabaseHelper
import com.bilsem.mutfakdolabi.objects.Grup
import com.bilsem.mutfakdolabi.objects.Product
import com.bilsem.mutfakdolabi.repository.FirestoreRepository
import com.bilsem.mutfakdolabi.viewmodels.ProductAddViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.android.synthetic.main.fragment_talep_ekle.view.*

class TalepEkleFragment : Fragment() {


    companion object {
        const val TAG = "TALEPEKLEFRAGMENT"
    }

    private val listenersToRemove = arrayListOf<ListenerRegistration>()
    private val productList = arrayListOf<Product>()
    private val productViewModel: ProductAddViewModel by activityViewModels()
    private val recyclerViewAdapterProduct = RecyclerViewAdapterProduct(productList)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_talep_ekle, container, false)

        view.buttonFragmentTalepEkleAddProduct.setOnClickListener { button ->
            val fragment = ProductAddFragment()
            fragment.show(childFragmentManager, ProductAddFragment.TAG)
        }
        view.recyclerViewFragmentTalepEkleProductList.layoutManager =
            LinearLayoutManager(requireContext())
        view.recyclerViewFragmentTalepEkleProductList.adapter = recyclerViewAdapterProduct

        setupDropdownMenu(view)
        view.buttonFragmentTalepEkleTamam.setOnClickListener {
            val ordertTitle =
                view.textInputLayoutFragmentTalepEkleTitleOfTalep.editText?.text.toString()
            val groupToAddOrder =
                view.exposedDropdownMenuFragmentTalepEkleGroupSelect.editText?.text.toString()
            val orderDesc =
                view.textInputLayoutFragmentTalepEkleDescriptionOfTalep.editText?.text.toString()


        }
        return view
    }

    private fun setupDropdownMenu(view: View?) {
        val groupsOfCurrenUser = arrayListOf<Grup>()
        val reg0 =
            FirestoreRepository.getGroupsOfCurrentUserById(FirebaseAuth.getInstance().currentUser.uid)
                .addSnapshotListener { value, error ->
                    if (error != null) {
                        Log.w(ToplulukFragment.TAG, error)
                        return@addSnapshotListener
                    }

                    for (document in value!!.documentChanges)
                        when (document.type) {
                            DocumentChange.Type.ADDED -> {
                                FirestoreRepository.getGroupById(document.document.id).get()
                                    .addOnCompleteListener {
                                        groupsOfCurrenUser.add(
                                            Grup(
                                                it.result!!.getString(DatabaseHelper.GROUP_NAME)!!,
                                                it.result!!.id,
                                                FirebaseAuth.getInstance().currentUser.uid == it.result?.getString(
                                                    DatabaseHelper.GROUP_OWNER
                                                )
                                            )
                                        )
                                    }
                            }

                            DocumentChange.Type.REMOVED ->
                                break
                            DocumentChange.Type.MODIFIED ->
                                break
                        }
                }
        listenersToRemove.add(reg0)
        val groupsId = arrayListOf<String>()
        groupsOfCurrenUser.forEach { groupsId.add(it.baslik) }

        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, groupsId)
        (view.exposedDropdownMenuFragmentTalepEkleGroupSelect.editText as? AutoCompleteTextView)?.setAdapter(
            adapter
        )

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productViewModel.selectedItem.observe(viewLifecycleOwner, Observer { product ->
            productList.add(product)
            recyclerViewAdapterProduct.notifyItemInserted(productList.size)
        })
    }

    override fun onStop() {
        super.onStop()
        listenersToRemove.forEach {
            it.remove()
        }
    }
}
