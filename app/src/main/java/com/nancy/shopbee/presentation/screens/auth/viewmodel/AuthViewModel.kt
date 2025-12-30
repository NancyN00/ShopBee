package com.nancy.shopbee.presentation.screens.auth.viewmodel

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import com.nancy.shopbee.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.lang.reflect.Constructor
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor (
    private val repository: AuthRepository
) : ViewModel() {

    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

    // UI Input States

    var name = mutableStateOf("")
    var email = mutableStateOf("")
    var password = mutableStateOf("")

    //  UI Status States (Encapsulated)
    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    fun onSignUp(onSuccess: () -> Unit) {
        if (!validateInputs()) return

        _isLoading.value = true
        repository.signUpWithEmail(email.value, password.value, name.value)
            .addOnCompleteListener { task ->
                _isLoading.value = false
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    _errorMessage.value = task.exception?.localizedMessage ?: "Sign up failed"
                }
            }
    }

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
     * Both SDKs provide an 'AuthCredential'
     */
    fun onSocialLogin(credential: AuthCredential, onSuccess: () -> Unit) {
        _isLoading.value = true
        repository.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // get the user signed in
                    val user = task.result?.user

                    // Refresh the user data to ensure Name/Email/Photo are fetched
                    user?.reload()?.addOnCompleteListener {
                        _isLoading.value = false
                        onSuccess()
                    }
                } else {
                    _isLoading.value = false
                    _errorMessage.value = task.exception?.localizedMessage
                }
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

    fun sendOtp(phone: String, activity: Activity, onSuccess: () -> Unit) {
        repository.sendOtp(
            phoneNumber = phone,
            activity = activity,
            onCodeSent = { id ->
                onSuccess()
            },
            onFailure = { error ->
                Log.e("PhoneAuth", "Error: ${error.message}")
            }
        )
    }

    fun verifyOtp(otpCode: String, onLoginSuccess: () -> Unit) {
        repository.signInWithOtp(otpCode) { success ->
            if (success) {
                onLoginSuccess()
            } else {
                viewModelScope.launch {
                    _toastMessage.emit("Invalid Code. Try again.")
                }
            }
        }
    }
}
