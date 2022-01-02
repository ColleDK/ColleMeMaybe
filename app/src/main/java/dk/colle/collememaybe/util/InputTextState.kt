package dk.colle.collememaybe.util

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class InputTextState() {
    var text: String by mutableStateOf("")
}