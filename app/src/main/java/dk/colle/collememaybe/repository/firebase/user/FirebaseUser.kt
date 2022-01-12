package dk.colle.collememaybe.repository.firebase.user

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import dk.colle.collememaybe.dto.UserDto
import kotlinx.coroutines.tasks.await

class FirebaseUser : BaseFirebaseUser {
    private val TAG = "FirebaseUser"
    private var db: FirebaseFirestore = Firebase.firestore
    private var storageRef = Firebase.storage.reference

    override suspend fun createUser(user: UserDto): UserDto {
        val newUser = hashMapOf(
            "userId" to user.userId,
            "name" to user.name,
            "birthday" to user.birthday,
            "email" to user.email,
            "phoneNumber" to user.phoneNumber,
            "profilePic" to "",
            "serverIds" to user.serverIds,
            "chatIds" to user.chatIds
        )

        val result = db.collection("users").add(newUser).await()
        return updateUser(
            user.copy(
//                userId = result.id,
                profilePic = user.profilePic?.let { uploadFile(uri = it, userId = user.userId).toString() }
            )
        )
    }

    override suspend fun updateUser(user: UserDto): UserDto {
        val updatedUser = hashMapOf(
            "userId" to user.userId,
            "name" to user.name,
            "birthday" to user.birthday,
            "email" to user.email,
            "phoneNumber" to user.phoneNumber,
            "profilePic" to user.profilePic,
            "serverIds" to user.serverIds,
            "chatIds" to user.chatIds
        )

        val result = db.collection("users").document(user.userId).set(updatedUser).await()
        return user
    }

    override suspend fun deleteUser(userId: String) {
        TODO("Not yet implemented")
    }


    override suspend fun getUser(userId: String): UserDto? {
        val user = db.collection("users").document(userId).get().await()
        Log.d(TAG, "Get user got user with data ${user.data}")
        val userModel = user.toObject(UserDto::class.java)
        Log.d(TAG, "Current user model is ${userModel.toString()}")
        return userModel
    }

    override suspend fun getUsers(userIds: List<String>): List<UserDto> {
        val result: MutableList<UserDto> = mutableListOf()
        userIds.forEach { userId ->
            db.collection("users").document(userId).get().await().toObject(UserDto::class.java)
                ?.let {
                    result.add(
                        it
                    )
                }
        }
        return result

    }

    override suspend fun getUsersFromServer(serverId: String): List<UserDto>? {
        TODO("Not yet implemented")
    }

    override suspend fun getAllUsers(): List<UserDto>? {
        TODO("Not yet implemented")
    }

    private suspend fun uploadFile(uri: String, userId: String): Uri? {
        return storageRef.child(userId).child("profilePic").putFile(Uri.parse(uri)).await().uploadSessionUri

    }

}