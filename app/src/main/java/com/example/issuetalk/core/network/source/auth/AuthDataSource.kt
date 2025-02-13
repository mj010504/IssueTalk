package com.example.issuetalk.core.network.source.auth


import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.oAuthCredential
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.tasks.await

import javax.inject.Inject

class AuthDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {

    fun loginFirebaseWithKakao(idToken : String) : Result<Unit> = runCatching {
        UserApiClient.instance.accessTokenInfo { accessTokenInfo, error ->
            val providerId = "oidc.kakao"
            val credential = oAuthCredential(providerId) {
                setIdToken(idToken)
            }

            firebaseAuth.signInWithCredential(credential)

        }
    }

     fun signUp(): Result<Unit> = runCatching {
//        firebaseAuth.createUserWithEmailAndPassword(email, password).await()
//            val user = firebaseAuth.currentUser
//            val profileUpdates = userProfileChangeRequest {
//                displayName = name
//            }
//            user!!.updateProfile(profileUpdates).await()
//        }
    }
}
