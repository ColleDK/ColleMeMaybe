package dk.colle.collememaybe.ui.startscreen

sealed class StartScreenEvent{
    data class OnServerClick(val serverId: String): StartScreenEvent()
    data class OnDMClick(val chatId: String): StartScreenEvent()
    object OnFriendslistClick: StartScreenEvent()
}
