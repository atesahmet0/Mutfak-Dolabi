package com.bilsem.mutfakdolabi.fragments

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.bilsem.mutfakdolabi.R
import com.bilsem.mutfakdolabi.helper.DatabaseHelper
import com.bilsem.mutfakdolabi.objects.Grup
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_grup_secenenekler.*
import kotlinx.android.synthetic.main.fragment_grup_secenenekler.view.*

class GrupDuzenleFragment : DialogFragment() {
    companion object {
        const val TAG = "GRUPDUZENLEDIALOGFRAGMENT"
        const val CURRENT_GROUP = "CURRENT_GROUP"
    }

    private lateinit var currentGrup: Grup
    private lateinit var mDatabase: FirebaseFirestore
    private lateinit var listOfViews: List<View>
    private lateinit var listOfViewsKisiEkle: List<View>

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        val insetDrawable = InsetDrawable(ColorDrawable(Color.TRANSPARENT), 20)
        dialog?.window?.setBackgroundDrawable(insetDrawable)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_grup_secenenekler, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentGrup = arguments!!.getSerializable(CURRENT_GROUP) as Grup
        mDatabase = FirebaseFirestore.getInstance()

        listOfViews = listOf<View>(view.buttonGrupSeceneklerKisiEkle,
        view.buttonGrupSeceneklerGrubuSil,
        view.buttonGrupSeceneklerIptal)

        listOfViewsKisiEkle = listOf<View>(view.textInputLayoutGrupSeceneklerKisiEkle,
            view.buttonGrupSeceneklerKisiEkleTamam,
            view.buttonGrupSeceneklerKisiEkleIptal)

        if (!currentGrup.isOwner) {
            listOfViews.get(1).visibility = View.GONE
        }
        buttonGrupSeceneklerIptal.setOnClickListener {
            dialog!!.dismiss()
        }
        buttonGrupSeceneklerGrubuSil.setOnClickListener {
            grupSil(currentGrup)
        }
        buttonGrupSeceneklerKisiEkle.setOnClickListener {
            kisiEkle(currentGrup)
        }

    }

    private fun kisiEkle(currentGrup: Grup) {
        changeViewsVisibility(listOfViews, View.GONE)
        changeViewsVisibility(listOfViewsKisiEkle, View.VISIBLE)

//        Iptal button clicked
        listOfViewsKisiEkle.get(2).setOnClickListener {
            changeViewsVisibility(listOfViews,View.VISIBLE)
            changeViewsVisibility(listOfViewsKisiEkle, View.GONE)
        }
//        Tamam button clicked
        listOfViewsKisiEkle.get(1).setOnClickListener {
            disableViews(listOfViewsKisiEkle)
            val textInputLayout = listOfViewsKisiEkle.get(0) as TextInputLayout
            val epostaToAddGroup = textInputLayout.editText?.text.toString().trim()

            textInputLayout.error=null
            if (!Patterns.EMAIL_ADDRESS.matcher(epostaToAddGroup).matches()||epostaToAddGroup.length==8){
                textInputLayout.error="Gecersiz Adres" }
            if (FirebaseAuth.getInstance().currentUser.email.equals(epostaToAddGroup)){
                textInputLayout.error="Kendi epostaniz" }
            val mDatabase = FirebaseFirestore.getInstance()
            var userIdToAddGroup: String

            mDatabase.collection(DatabaseHelper.USERS).whereEqualTo(DatabaseHelper.USER_EMAIL,epostaToAddGroup).get().addOnCompleteListener {
                it.result?.forEach {
                    it.reference.collection(DatabaseHelper.USER_MEMBER_OF).document(currentGrup.uid).set(
                        mapOf(DatabaseHelper.DATE to System.currentTimeMillis()/1000))
                    userIdToAddGroup=it.id
                    if (checkUserExistInGroup(userIdToAddGroup,currentGrup)){

                    }

                    mDatabase.collection(DatabaseHelper.GROUPS).document(currentGrup.uid)
                        .collection(DatabaseHelper.GROUP_MEMBERS).document(userIdToAddGroup).set(mapOf(DatabaseHelper.GROUP_MEMBER_RANK to DatabaseHelper.GROUP_CASUAL)).addOnSuccessListener {
                            Toast.makeText(context,"Basarili",Toast.LENGTH_SHORT).show()
                            dialog!!.dismiss()
                        }.addOnFailureListener {
                            enableViews(listOfViewsKisiEkle)
                            Toast.makeText(context,it.toString(),Toast.LENGTH_SHORT).show()
                        }
                }

            }


        }
    }

    private fun checkUserExistInGroup(userIdToAddGroup: String, currentGrup: Grup): Boolean {
        return false
    }

    private fun grupSil(currentGrup: Grup) {
        disableViews(listOfViews)
        val peopleInGroup = arrayListOf<String>()
        mDatabase.collection(DatabaseHelper.GROUPS).document(currentGrup.uid)
            .collection(DatabaseHelper.GROUP_MEMBERS).get().addOnCompleteListener { it ->
                it.result?.forEachIndexed { index, userId ->
                    peopleInGroup.add(userId.id)
                }
                for (userId in peopleInGroup) {
                    mDatabase.collection(DatabaseHelper.USERS).document(userId)
                        .collection(DatabaseHelper.USER_MEMBER_OF)
                        .document(currentGrup.uid).delete()
                }
                mDatabase.collection(DatabaseHelper.GROUPS).document(currentGrup.uid).delete()
                    .addOnCompleteListener {
                        Toast.makeText(context, "Grup silindi", Toast.LENGTH_SHORT).show()
                        dialog!!.dismiss()
                        enableViews(listOfViews)
                    }
            }
    }
    fun disableViews(viewsToDisable :List<View>){
        viewsToDisable.forEach { it.isClickable=false }
        dialog!!.setCancelable(false)
    }
    fun enableViews(viewsToEnable: List<View>){
        viewsToEnable.forEach { it.isClickable=true }
        dialog!!.setCancelable(true)
    }
    fun changeViewsVisibility(viewsToChangeVisibility: List<View>,visibility: Int){
        viewsToChangeVisibility.forEach {
            it.clearAnimation()
            it.visibility=visibility
            it.invalidate()
        }
        if (!currentGrup.isOwner) {
            listOfViews.get(1).visibility = View.GONE
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        val parentFragment = parentFragment
        if (parentFragment is DialogInterface.OnDismissListener){
            (parentFragment as DialogInterface.OnDismissListener).onDismiss(dialog)
        }
    }
}
