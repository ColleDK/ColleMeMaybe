package dk.colle.collememaybe.model

import android.net.Uri
import dk.colle.collememaybe.dto.ServerDto

data class ServerModel(
    val serverId: String,
    val serverPicUri: String?,
    val messages: List<MessageModel>,
    val users: List<UserModel>,
) {
    companion object{
        fun ServerModel.toDto(): ServerDto {
            return ServerDto(
                serverId, serverPicUri, messages, users
            )
        }
    }
}
