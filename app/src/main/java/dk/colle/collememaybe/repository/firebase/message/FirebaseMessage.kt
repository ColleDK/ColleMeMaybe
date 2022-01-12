package dk.colle.collememaybe.repository.firebase.message

import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import dk.colle.collememaybe.dto.MessageDto
import kotlinx.coroutines.tasks.await

class FirebaseMessage : BaseFirebaseMessage {
    private val TAG = "FirebaseMessage"
    private var db: FirebaseFirestore = Firebase.firestore
    private var storageRef: StorageReference = Firebase.storage.reference


    override suspend fun getMessage(messageId: String): MessageDto? {
        return db.collection("messages").document(messageId).get().await()
            .toObject(MessageDto::class.java)
    }

    override suspend fun sendMessage(chatId: String, message: MessageDto): MessageDto? {
        val newMessage = hashMapOf(
            "messageId" to "",
            "senderId" to message.senderId,
            "message" to message.message,
            "picture" to message.picture,
            "timestamp" to message.timestamp
        )

        val result = db.collection("messages").add(newMessage).await()
        return updateMessage(messageId = result.id,
            newMessage = message.copy(
                messageId = result.id,
                picture = message.picture?.let { uploadFile(it, result.id).toString() }
            )
        )
    }

    override suspend fun deleteMessage(chatId: String, messageId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun updateMessage(messageId: String, newMessage: MessageDto): MessageDto? {
        val updatedMessage = hashMapOf(
            "messageId" to "",
            "senderId" to newMessage.senderId,
            "message" to newMessage.message,
            "picture" to newMessage.picture,
            "timestamp" to newMessage.timestamp
        )
        db.collection("messages").document(messageId).set(updatedMessage).await()
        return newMessage
    }

    private suspend fun uploadFile(uri: String, messageId: String): Uri? {
        return storageRef.child(messageId).child("messagePic").putFile(Uri.parse(uri))
            .await().uploadSessionUri

    }

}