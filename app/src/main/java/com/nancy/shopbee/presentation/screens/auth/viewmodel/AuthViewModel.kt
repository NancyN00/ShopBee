package com.nancy.shopbee.presentation.screens.auth.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.AuthCredential
import com.nancy.shopbee.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.reflect.Constructor
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor (
    private val repository: AuthRepository
) : ViewModel() {

    // UI Input States
    var email = mutableStateOf("")
    var password = mutableStateOf("")

    //  UI Status States (Encapsulated)
    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    // Email Sign Up ---
    fun onSignUp(onSuccess: () -> Unit) {
        if (!validateInputs()) return

        _isLoading.value = true
        repository.signUp(email.value, password.value)
            .addOnCompleteListener { task ->
                _isLoading.value = false
                if (task.isSuccessful) onSuccess()
                else _errorMessage.value = task.exception?.localizedMessage
            }
    }

    // Email Sign In ---
    fun onSignIn(onSuccess: () -> Unit) {
        if (!validateInputs()) return

        _isLoading.value = true
        repository.signIn(email.value, password.value)
            .addOnCompleteListener { task ->
                _isLoading.value = false
                if (task.isSuccessful) onSuccess()
                else _errorMessage.value = task.exception?.localizedMessage
            }
    }

    /** Social Login (Google/Facebook)
     * Both SDKs eventually provide an 'AuthCredential'
     */
    fun onSocialLogin(credential: AuthCredential, onSuccess: () -> Unit) {
        _isLoading.value = true
        repository.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                _isLoading.value = false
                if (task.isSuccessful) onSuccess()
                else _errorMessage.value = task.exception?.localizedMessage
            }
    }

    fun logout() {
        repository.signOut()
    }

    // helper for simple validation
    private fun validateInputs(): Boolean {
        if (email.value.isBlank() || password.value.isBlank()) {
            _errorMessage.value = "Fields cannot be empty"
            return false
        }
        _errorMessage.value = null
        return true
    }
}
