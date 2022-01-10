package dk.colle.collememaybe.ui.auth.login_user

sealed class LoginUserEvent {
    data class OnEditEmail(val email: String) : LoginUserEvent()
    data class OnEditPassword(val password: String) : LoginUserEvent()
    object ToggleShowPassword : LoginUserEvent()
    object OnLoginUser : LoginUserEvent()
    data class OnClickResetPassword(val email: String) : LoginUserEvent()
}