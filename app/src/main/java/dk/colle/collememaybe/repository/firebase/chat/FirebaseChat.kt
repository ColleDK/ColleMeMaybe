package dk.colle.collememaybe.repository.firebase.chat

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dk.colle.collememaybe.dto.ChatDto
import kotlinx.coroutines.tasks.await

class FirebaseChat : BaseFirebaseChat {
    private val TAG = "FirebaseChat"
    private var db: FirebaseFirestore = Firebase.firestore

    override suspend fun getChat(userId: String, chatId: String): ChatDto? {
        val user = db.collection("users").document(userId).get().await()
        user.data?.forEach {
            Log.d(TAG, "User data is ${it.toString()}")
        }
        return null
    }


    override suspend fun getChats(userId: String): List<ChatDto>? {
        db.collection("users").document(userId).get().await()
        return null
    }

    override suspend fun createChat(newChat: ChatDto): String {
        val messageIds: MutableList<String> = mutableListOf()
        newChat.messages.forEach { messageIds.add(it.messageId) }

        val chat = hashMapOf(
            "chatId" to null,
            "users" to newChat.users,
            "messageIds" to messageIds
        )

        val result = db.collection("chats").add(chat).await()

        return result.id
    }

    override suspend fun deleteChat(chatId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun updateChat(chatId: String, chatDto: ChatDto) {
        TODO("Not yet implemented")
    }

}