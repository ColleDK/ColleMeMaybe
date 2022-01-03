package dk.colle.collememaybe.repository.auth

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser
import dk.colle.collememaybe.repository.firebase.BaseAuthenticator
import java.lang.Exception
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authenticator: BaseAuthenticator
) : BaseAuthRepository {
    override suspend fun signInWithEmailPassword(
        email: String,
        password: String
    ) : FirebaseUser? {
        try {
            return authenticator.signInWithEmailAndPassword(
                email = email,
                password = password
            )
        } catch (e: FirebaseAuthWeakPasswordException){ // Bad password
            throw e
        } catch (e: FirebaseAuthUserCollisionException){ // User already exists
            throw e
        } catch (e: Exception){
            throw e
        }
    }

    override suspend fun signUpWithEmailPassword(
        email: String,
        password: String
    ) : FirebaseUser? {
        try {
            return authenticator.signUpWithEmailAndPassword(
                email = email,
                password = password
            )
        } catch (e : FirebaseAuthInvalidCredentialsException){ // Email or password incorrect
            throw e
        } catch (e: Exception) {
            throw e
        }
    }

    override fun signOut(): FirebaseUser? {
        return authenticator.signOut()
    }

    override fun getCurrentUser(): FirebaseUser? {
        return authenticator.getUser()
    }

    override suspend fun sendResetPassword(email: String): Boolean {
        authenticator.sendPasswordReset(email = email)
        return true
    }

}