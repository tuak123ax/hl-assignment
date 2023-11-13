package com.minhtu.jokeesingleservingapp.model

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FirebaseHelper private constructor() {
    companion object{
        private var databaseReference: DatabaseReference? = null
        fun getFirebaseReference() : DatabaseReference{
            if(databaseReference == null) {
                databaseReference = Firebase.database.reference
            }
            return databaseReference as DatabaseReference
        }
    }
}