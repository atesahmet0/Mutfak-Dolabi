package com.bilsem.mutfakdolabi.Fragmentler

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.bilsem.mutfakdolabi.Adapterler.RecyclerViewAdapterGrup
import com.bilsem.mutfakdolabi.Objeler.Grup
import com.bilsem.mutfakdolabi.R
import com.bilsem.mutfakdolabi.helper.DatabaseHelper
import com.bilsem.mutfakdolabi.repository.FirestoreRepository
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirestoreRegistrar
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.android.synthetic.main.fragment_topluluk.view.*

class ToplulukFragment : Fragment(), DialogInterface.OnDismissListener{

    companion object{
        const val TAG="TOPLULUKFRAGMENT"
    }

    private var groupsOfCurrentUser = arrayListOf<Grup>()
    private var recyclerViewAdapterGrup = RecyclerViewAdapterGrup(grupList)
    private var  firebaseAuth = FirebaseAuth.getInstance()

    private lateinit var progressBar: LinearProgressIndicator
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =inflater.inflate(R.layout.fragment_topluluk,container,false)

        progressBar = view.progressIndicatorLinearToplulukIsLoading
        recyclerView=view.recyclerViewGrup

        view.buttonToplulukAddGrup.setOnClickListener {
            grupEkleDialog()
        }

        recyclerViewAdapterGrup.mGrupAdapterItemClickListener=settingsClicked

        view.recyclerViewGrup.layoutManager=LinearLayoutManager(context)
        view.recyclerViewGrup.adapter=recyclerViewAdapterGrup
        populateAdapter()
        return view
    }

    private fun populateAdapter() {
        var registrationGroupsOfCurrentUser : ListenerRegistration
        var groupsOfCurrentUserById = arrayListOf<String>()
        FirestoreRepository.getGroupsOfCurrentUserById(firebaseAuth.currentUser.uid).addSnapshotListener { value, error ->
            if (error != null){
                Log.w(TAG,error)
                return@addSnapshotListener
            }
            //TODO("burada kalmistik")
            for (document in value!!.documentChanges)
                when(document.type){
                    DocumentChange.Type.ADDED ->
                        break
                    DocumentChange.Type.REMOVED ->
                        break
                    DocumentChange.Type.MODIFIED ->
                        break
        }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


    private fun setLoading(isLoading: Boolean){
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        recyclerView.visibility = if (isLoading) View.GONE else View.VISIBLE

    }
    private fun grupEkleDialog(){
        val fragment= GrupEkleFragment()
        fragment.isCancelable= false
        fragment.show(childFragmentManager,GrupEkleFragment.TAG)

    }

    val settingsClicked = object : RecyclerViewAdapterGrup.grupAdapterItemClickListener{
        override fun onItemClick(view: View, position: Int, dataList: List<Grup>) {
            val fragment = GrupDuzenleFragment()
            val bundle = Bundle()
            bundle.putSerializable(GrupDuzenleFragment.CURRENT_GROUP,dataList.get(position))
            fragment.arguments=bundle
            fragment.show(childFragmentManager,GrupDuzenleFragment.TAG)
        }
    }
    override fun onDismiss(dialog: DialogInterface?) {

    }

}