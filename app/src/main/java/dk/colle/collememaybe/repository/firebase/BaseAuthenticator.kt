package dk.colle.collememaybe.repository.firebase

import com.google.firebase.auth.FirebaseUser

interface BaseAuthenticator {

    suspend fun signUpWithEmailAndPassword(email: String, password: String, callBack: (result: FirebaseUser?) -> Unit)

    suspend fun signInWithEmailAndPassword(email: String, password: String, callBack: (result: FirebaseUser?) -> Unit)

    fun signOut() : FirebaseUser?

    fun getUser() : FirebaseUser?

    suspend fun sendPasswordReset(email: String)
}