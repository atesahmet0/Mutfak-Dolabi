package com.bilsem.mutfakdolabi.repository

import android.content.Context
import com.bilsem.mutfakdolabi.helpers.DatabaseHelper
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

object FirestoreRepository {

    private val groupsReference = remoteFireStoreRepository().collection(DatabaseHelper.GROUPS)
    private val usersReference = remoteFireStoreRepository().collection(DatabaseHelper.USERS)

    fun remoteFireStoreRepository() : FirebaseFirestore{
        return FirebaseFirestore.getInstance()
    }

    operator fun invoke(context: Context) : FirestoreRepository{
        return this
    }
    fun getGroupsOfCurrentUserById(currentUserId: String) : Query {
        return usersReference.document(currentUserId).collection(DatabaseHelper.USER_MEMBER_OF)
    }
    fun getGroupsByIdList(groupIds: List<String>): Query{
        return groupsReference.whereIn(FieldPath.documentId(), groupIds)
    }
    fun getGroupById(groupId: String): DocumentReference {
        return groupsReference.document(groupId)
    }
}