package dk.colle.collememaybe

class ResultCommand(
    val message: String? = null,
    val status: Status
){
    enum class Status {SUCCESS, LOADING, ERROR}
}