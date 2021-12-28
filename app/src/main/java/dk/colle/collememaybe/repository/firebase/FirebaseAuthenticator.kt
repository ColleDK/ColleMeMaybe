package dk.colle.collememaybe.repository.firebase

import android.util.Log
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FirebaseAuthenticator: BaseAuthenticator {
    private val TAG = "Authenticator"

    override suspend fun signUpWithEmailAndPassword(
        email: String,
        password: String,
        callBack: (result: FirebaseUser?) -> Unit,
    ) {
        Log.d(TAG, "Signing up with email and pass")
        Firebase.auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            when {
                it.isSuccessful -> {
                    Log.d(TAG, "Creating success")
                    callBack(getUser())
                }
                it.isComplete -> {
                    Log.d(TAG, "Creating complete")
                }
                else -> {
                    Log.d(TAG, "Creating fail")
                }
            }
        }
    }

    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String,
        callBack: (result: FirebaseUser?) -> Unit
    ) {
        Firebase.auth.signInWithEmailAndPassword(email, password).addOnCompleteListener{
            callBack(getUser())
        }
    }

    override fun signOut(): FirebaseUser? {
        Firebase.auth.signOut()
        return getUser()
    }

    override fun getUser(): FirebaseUser? {
        return Firebase.auth.currentUser
    }

    override suspend fun sendPasswordReset(email: String) {
        Firebase.auth.sendPasswordResetEmail(email)
    }

}