package dk.colle.collememaybe.ui.auth.create_user

sealed class CreateUserEvent{
    data class OnEditName(val name: String) : CreateUserEvent()
    data class OnEditBirthday(val birthday: String) : CreateUserEvent()
    data class OnEditEmail(val email: String) : CreateUserEvent()
    data class OnEditPassword(val password: String) : CreateUserEvent()
    data class OnEditPhoneNumber(val phoneNumber: String) : CreateUserEvent()
    object OnGoToLoginClicked : CreateUserEvent()
    object ToggleShowPassword : CreateUserEvent()
    object OnCreateUser: CreateUserEvent()
}
