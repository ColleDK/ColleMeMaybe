package dk.colle.collememaybe.repository.firebase

import android.util.Log
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class FirebaseAuthenticator: BaseAuthenticator {
    private val TAG = "Authenticator"

    override suspend fun signUpWithEmailAndPassword(
        email: String,
        password: String,
    ) : FirebaseUser? {
        Log.d(TAG, "Signing up with email and pass")
        Firebase.auth.createUserWithEmailAndPassword(email, password).await()
        return Firebase.auth.currentUser
    }

    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String,
    ) : FirebaseUser? {
        Firebase.auth.signInWithEmailAndPassword(email, password).await()
        return Firebase.auth.currentUser
    }

    override fun signOut(): FirebaseUser? {
        Firebase.auth.signOut()
        return Firebase.auth.currentUser
    }

    override fun getUser(): FirebaseUser? {
        return Firebase.auth.currentUser
    }

    override suspend fun sendPasswordReset(email: String) {
        Firebase.auth.sendPasswordResetEmail(email)
    }

}