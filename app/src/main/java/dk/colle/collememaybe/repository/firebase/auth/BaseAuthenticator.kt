package dk.colle.collememaybe.repository.firebase.auth

import com.google.firebase.auth.FirebaseUser

interface BaseAuthenticator {

    suspend fun signUpWithEmailAndPassword(email: String, password: String): FirebaseUser?

    suspend fun signInWithEmailAndPassword(email: String, password: String): FirebaseUser?

    fun signOut() : FirebaseUser?

    fun getUser() : FirebaseUser?

    suspend fun sendPasswordReset(email: String)
}