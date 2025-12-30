package com.nancy.shopbee.domain.repository

import android.app.Activity
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.Firebase
import com.google.firebase.FirebaseException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.auth
import com.google.firebase.auth.userProfileChangeRequest
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val auth: FirebaseAuth
) {

    private var storedVerificationId: String? = null

    // AuthRepository.kt
    fun signUpWithEmail(email: String, pass: String, name: String): Task<Void?> {
        // user created
        return auth.createUserWithEmailAndPassword(email, pass).continueWithTask { task ->
            if (task.isSuccessful) {
                // attach name to account
                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .build()

                auth.currentUser?.updateProfile(profileUpdates)
            } else {
                Tasks.forException(task.exception!!)
            }
        }
    }
    fun signIn(email: String, password: String) =
        auth.signInWithEmailAndPassword(email, password)
    fun signInWithCredential(credential: AuthCredential): Task<AuthResult> {
        return auth.signInWithCredential(credential)
    }

    fun signOut() = auth.signOut()

    fun sendOtp(
        phoneNumber: String,
        activity: Activity,
        onCodeSent: (String) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val options = PhoneAuthOptions.newBuilder(Firebase.auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    // Auto-verification logic
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    onFailure(e)
                }

                override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {
                    storedVerificationId = verificationId
                    onCodeSent(verificationId)
                }
            })
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    //verify the code
    fun signInWithOtp(otpCode: String, onResult: (Boolean) -> Unit) {
        val id = storedVerificationId ?: return onResult(false)
        val credential = PhoneAuthProvider.getCredential(id, otpCode)

        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                onResult(task.isSuccessful)
            }
    }

    fun getCurrentUser() = auth.currentUser
}
