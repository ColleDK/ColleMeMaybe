package dk.colle.collememaybe.repository

import com.google.firebase.auth.FirebaseUser

interface BaseAuthRepository {
    suspend fun signInWithEmailPassword(email:String , password:String, callback: (result: FirebaseUser?) -> Unit)

    suspend fun signUpWithEmailPassword(email: String , password: String, callback: (result: FirebaseUser?) -> Unit)

    fun signOut() : FirebaseUser?

    fun getCurrentUser() : FirebaseUser?

    suspend fun sendResetPassword(email : String) : Boolean
}