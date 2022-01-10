package dk.colle.collememaybe.dto

import android.net.Uri
import dk.colle.collememaybe.model.MessageModel
import dk.colle.collememaybe.model.ServerModel
import dk.colle.collememaybe.model.UserModel

data class ServerDto(
    val serverId: String,
    val serverPicUri: Uri,
    val messages: List<MessageModel>,
    val users: List<UserModel>,
) {
    companion object{
        fun ServerDto.toModel(): ServerModel {
            return ServerModel(serverId, serverPicUri, messages, users)
        }
    }
}
