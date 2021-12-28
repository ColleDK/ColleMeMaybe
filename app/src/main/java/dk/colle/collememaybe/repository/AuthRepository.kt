package dk.colle.collememaybe.repository

import com.google.firebase.auth.FirebaseUser
import dk.colle.collememaybe.repository.firebase.BaseAuthenticator
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authenticator: BaseAuthenticator
) : BaseAuthRepository {
    override suspend fun signInWithEmailPassword(
        email: String,
        password: String,
        callback: (result: FirebaseUser?) -> Unit
    ) {
        authenticator.signInWithEmailAndPassword(
            email = email,
            password = password,
            callBack = callback
        )
    }

    override suspend fun signUpWithEmailPassword(
        email: String,
        password: String,
        callback: (result: FirebaseUser?) -> Unit,
    ) {
        authenticator.signUpWithEmailAndPassword(
            email = email,
            password = password,
            callBack = callback
        )
    }

    override fun signOut(): FirebaseUser? {
        authenticator.signOut()
        return getCurrentUser()
    }

    override fun getCurrentUser(): FirebaseUser? {
        return authenticator.getUser()
    }

    override suspend fun sendResetPassword(email: String): Boolean {
        authenticator.sendPasswordReset(email = email)
        return true
    }

}