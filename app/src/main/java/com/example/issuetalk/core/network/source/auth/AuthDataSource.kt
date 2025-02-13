package com.example.issuetalk.core.network.source.auth


import UserInformationRequest
import UserInformationResponse
import com.google.firebase.firestore.toObject
import com.example.issuetalk.core.network.constant.USER_INFORMATION_COLLECTION
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.oAuthCredential
import com.google.firebase.firestore.FirebaseFirestore

import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.tasks.await

import javax.inject.Inject

class AuthDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFireStore : FirebaseFirestore
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

    suspend fun verifyRegistered() : Result<Boolean> = runCatching {
        val userId = firebaseAuth.currentUser!!.uid

        val document = firebaseFireStore.collection(USER_INFORMATION_COLLECTION)
            .document(userId)
            .get()
            .await()

        document.toObject<UserInformationResponse>() != null
    }

    suspend fun getUserInformation() : Result<UserInformationResponse> = runCatching {
        val userId = firebaseAuth.currentUser!!.uid

        val document = firebaseFireStore.collection(USER_INFORMATION_COLLECTION)
            .document(userId)
            .get()
            .await()

        requireNotNull(document.toObject<UserInformationResponse>())
    }

    suspend fun setUserInformation(userInformationRequest: UserInformationRequest) : Result<Unit> = runCatching {
        val userId = firebaseAuth.currentUser!!.uid

        firebaseFireStore.collection(USER_INFORMATION_COLLECTION)
            .document(userId)
            .set(userInformationRequest)
            .await()

    }

    suspend fun deleteUserInformation() : Result<Unit> = runCatching {
        val userId = firebaseAuth.currentUser!!.uid

        firebaseFireStore.collection(USER_INFORMATION_COLLECTION)
            .document(userId)
            .delete()
            .await()
    }



}
