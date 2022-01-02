package dk.colle.collememaybe.util

class ResultCommand(
    val message: String,
    val status: Status
){
    enum class Status {SUCCESS, LOADING, ERROR}
}