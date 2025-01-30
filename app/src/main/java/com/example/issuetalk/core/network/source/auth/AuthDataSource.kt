package com.example.issuetalk.core.network.source.auth

import com.example.issuetalk.feature.auth.LoginViewModel.LoginEvent
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {
    suspend fun loginFirebase(email: String, password: String): Result<Unit> = runCatching {
        firebaseAuth.signInWithEmailAndPassword(
            email,
            password
        ).await()
    }

    suspend fun signUpFirebase(email: String, password: String, name : String) : Result<Unit> = runCatching {

    }
}