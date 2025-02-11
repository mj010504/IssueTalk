package com.example.issuetalk.core.network.source.auth

import com.example.issuetalk.feature.auth.LoginViewModel.LoginEvent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {
    suspend fun loginKakao(): Result<Unit> = runCatching {
    }

    suspend fun signUp(): Result<Unit> = runCatching {
//        firebaseAuth.createUserWithEmailAndPassword(email, password).await()
//            val user = firebaseAuth.currentUser
//            val profileUpdates = userProfileChangeRequest {
//                displayName = name
//            }
//            user!!.updateProfile(profileUpdates).await()
//        }
    }
}
