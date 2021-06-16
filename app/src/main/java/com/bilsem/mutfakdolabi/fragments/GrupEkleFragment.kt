package com.bilsem.mutfakdolabi.fragments

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.bilsem.mutfakdolabi.R
import com.bilsem.mutfakdolabi.helper.DatabaseHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_grup_ekle.view.*

class GrupEkleFragment : DialogFragment() {
    companion object{
        const val TAG="GRUPEKLEDIALOGFRAGMENT"
    }
    val ISLEM_BASARILI = "Grup basariyla eklendi"



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_grup_ekle, container, false)

        view.buttonFragmentGrupEkleIptal.setOnClickListener {
            dialog!!.dismiss()
        }
        view.buttonFragmentGrupEkleTamam.setOnClickListener {
            disableButtons(
                listOf(
                    view.buttonFragmentGrupEkleTamam,
                    view.buttonFragmentGrupEkleIptal
                )
            )
            val isim = view.textInputLayoutFragmentGrupEkleIsim.editText!!.text.toString()
            grupEkle(isim)
            listOf(view.buttonFragmentGrupEkleTamam, view.buttonFragmentGrupEkleIptal)
        }
        return view
    }

    private fun grupEkle(isim: String) {
        val database = FirebaseFirestore.getInstance()
        val mauth = FirebaseAuth.getInstance()
        database.collection(DatabaseHelper.GROUPS).document().apply {
            this.set(
                mapOf(
                    DatabaseHelper.GROUP_NAME to isim,
                    DatabaseHelper.DATE to System.currentTimeMillis() / 1000,
                    DatabaseHelper.GROUP_OWNER to mauth.currentUser.uid
                )
            ).addOnSuccessListener {
                this.collection(DatabaseHelper.GROUP_MEMBERS).document(mauth.currentUser.uid).set(
                    mapOf(DatabaseHelper.GROUP_MEMBER_RANK to DatabaseHelper.GROUP_MEMBER_RANK_OWNER)
                ).addOnSuccessListener {
                    database.collection(DatabaseHelper.USERS).document(mauth.currentUser.uid)
                        .collection(DatabaseHelper.USER_MEMBER_OF).document(id)
                        .set(mapOf(DatabaseHelper.DATE to System.currentTimeMillis() / 1000))
                        .addOnSuccessListener {
                            islemBasarili()
                    }.addOnFailureListener {
                        islemBasarisiz(it.toString())
                    }
                }
            }.addOnFailureListener {
                islemBasarisiz(it.toString())
            }
            }
    }


    private fun islemBasarili() {
        Toast.makeText(activity, ISLEM_BASARILI, Toast.LENGTH_SHORT).show()
        dialog!!.dismiss()
    }

    private fun islemBasarisiz(str: String) {
        Toast.makeText(activity, str, Toast.LENGTH_SHORT).show()
        dialog!!.dismiss()
    }


    private fun disableButtons(buttons: List<Button>) {
        for (i in buttons) {
            i.isClickable = false
        }
        dialog?.setCancelable(false)
    }

    private fun enableButtons(buttons: List<Button>) {
        for (i in buttons) {
            i.isClickable = true
        }
        dialog?.setCancelable(true)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        val insetDrawable = InsetDrawable(ColorDrawable(Color.TRANSPARENT), 20)
        dialog?.window?.setBackgroundDrawable(insetDrawable)
        dialog?.setCancelable(true)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        val parentFragment = parentFragment
        if (parentFragment is DialogInterface.OnDismissListener){
            (parentFragment as DialogInterface.OnDismissListener).onDismiss(dialog)
        }
    }

}