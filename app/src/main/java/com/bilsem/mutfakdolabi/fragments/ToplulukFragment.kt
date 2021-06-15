package com.bilsem.mutfakdolabi.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bilsem.mutfakdolabi.R
import com.bilsem.mutfakdolabi.adapters.RecyclerViewAdapterGrup
import com.bilsem.mutfakdolabi.helper.DatabaseHelper
import com.bilsem.mutfakdolabi.objects.Grup
import com.bilsem.mutfakdolabi.repository.FirestoreRepository
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.android.synthetic.main.fragment_topluluk.view.*

class ToplulukFragment : Fragment(), DialogInterface.OnDismissListener {

    companion object {
        const val TAG = "TOPLULUKFRAGMENT"
    }

    private var groupsOfCurrentUser = arrayListOf<Grup>()
    private var recyclerViewAdapterGrup = RecyclerViewAdapterGrup(groupsOfCurrentUser)
    private var firebaseAuth = FirebaseAuth.getInstance()

    private val registrationsToRemoveOnStop = arrayListOf<ListenerRegistration>()

    private lateinit var progressBar: LinearProgressIndicator
    private lateinit var recyclerViewGroupsOfCurrentUser: RecyclerView
    private lateinit var textViewNothingToShowHere: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_topluluk, container, false)

        progressBar = view.progressIndicatorLinearToplulukFragmentIsLoading
        recyclerViewGroupsOfCurrentUser = view.recyclerViewToplulukGroupsList
        textViewNothingToShowHere = view.textViewToglulukFragmentNothingToShowHere

        view.buttonToplulukFragmentAddGroup.setOnClickListener {
            grupEkleDialog()
        }

        recyclerViewAdapterGrup.mGrupAdapterItemClickListener = settingsClicked

        view.recyclerViewToplulukGroupsList.layoutManager = LinearLayoutManager(context)
        view.recyclerViewToplulukGroupsList.adapter = recyclerViewAdapterGrup
        populateAdapter()
        return view
    }

    private fun populateAdapter() {
        setSituationOfFragment(Situation.LOADING)
        val reg0 = FirestoreRepository.getGroupsOfCurrentUserById(firebaseAuth.currentUser.uid).addSnapshotListener { value, error ->
            if (error != null) {
                Log.w(TAG, error)
                return@addSnapshotListener
            }
            if (value!!.isEmpty) setSituationOfFragment(Situation.EMPTY) else setSituationOfFragment(
                Situation.LOADED
            )
            for (document in value.documentChanges)
                when (document.type) {
                    DocumentChange.Type.ADDED -> {
                        FirestoreRepository.getGroupById(document.document.id).get()
                            .addOnCompleteListener {
                                addGrupToList(
                                    Grup(
                                        it.result!!.getString(DatabaseHelper.GROUP_NAME)!!,
                                        it.result!!.id,
                                        firebaseAuth.currentUser.uid == it.result?.getString(
                                            DatabaseHelper.GROUP_OWNER
                                        )
                                    )
                                )
                            }
                    }

                    DocumentChange.Type.REMOVED ->
                        deleteGrupFromListById(document.document.id)
                    DocumentChange.Type.MODIFIED ->
                        break
                }
        }
        registrationsToRemoveOnStop.add(reg0)
    }

    private fun addGrupToList(grup: Grup) {
        groupsOfCurrentUser.add(grup)
        recyclerViewAdapterGrup.notifyItemInserted(groupsOfCurrentUser.size)
    }

    private fun deleteGrupFromListById(grupId: String) {
        groupsOfCurrentUser.forEachIndexed { index, grupIterate ->
            if (grupId == grupIterate.uid) {
                groupsOfCurrentUser.removeAt(index)
                recyclerViewAdapterGrup.notifyItemRemoved(index)
                return
            }
        }
    }

    private fun setSituationOfFragment(situation: Situation) {
        when (situation) {
            Situation.LOADING -> {
                progressBar.visibility = View.VISIBLE
                recyclerViewGroupsOfCurrentUser.visibility = View.GONE
                textViewNothingToShowHere.visibility = View.GONE
            }
            Situation.EMPTY -> {
                progressBar.visibility = View.GONE
                recyclerViewGroupsOfCurrentUser.visibility = View.GONE
                textViewNothingToShowHere.visibility = View.VISIBLE
            }
            Situation.LOADED -> {
                progressBar.visibility = View.GONE
                recyclerViewGroupsOfCurrentUser.visibility = View.VISIBLE
                textViewNothingToShowHere.visibility = View.GONE
            }

        }
    }

    private fun grupEkleDialog() {
        val fragment = GrupEkleFragment()
        fragment.isCancelable = false
        fragment.show(childFragmentManager, GrupEkleFragment.TAG)

    }

    val settingsClicked = object : RecyclerViewAdapterGrup.grupAdapterItemClickListener {
        override fun onItemClick(view: View, position: Int, dataList: List<Grup>) {
            val fragment = GrupDuzenleFragment()
            val bundle = Bundle()
            bundle.putSerializable(GrupDuzenleFragment.CURRENT_GROUP, dataList.get(position))
            fragment.arguments = bundle
            fragment.show(childFragmentManager, GrupDuzenleFragment.TAG)
        }
    }

    override fun onDismiss(dialog: DialogInterface?) {}

    private enum class Situation {
        LOADING,
        LOADED,
        EMPTY
    }

    override fun onStop() {
        super.onStop()
        registrationsToRemoveOnStop.forEach {
            it.remove()
        }
    }
}