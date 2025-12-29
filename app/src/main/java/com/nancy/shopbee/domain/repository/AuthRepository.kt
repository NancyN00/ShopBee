package com.nancy.shopbee.domain.repository

import com.google.firebase.Firebase
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.auth
import javax.inject.Inject


class AuthRepository @Inject constructor(){
    private val auth = Firebase.auth

    // Email & Password
    fun signUp(email: String, password: String) =
        auth.createUserWithEmailAndPassword(email, password)

    fun signIn(email: String, password: String) =
        auth.signInWithEmailAndPassword(email, password)

    // Social Login (Google & Facebook use Credentials)
    fun signInWithCredential(credential: AuthCredential) =
        auth.signInWithCredential(credential)

    fun signOut() = auth.signOut()

    fun getCurrentUser() = auth.currentUser
}
