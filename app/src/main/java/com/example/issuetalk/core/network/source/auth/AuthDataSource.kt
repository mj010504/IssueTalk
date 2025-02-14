package com.example.issuetalk.core.network.source.auth


import UserInformationRequest
import UserInformationResponse
import com.example.issuetalk.core.network.constant.OIDC_KAKAO_PROVIDER_ID
import com.google.firebase.firestore.toObject
import com.example.issuetalk.core.network.constant.USER_INFORMATION_COLLECTION
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
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
            val credential = oAuthCredential(OIDC_KAKAO_PROVIDER_ID) {
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

fun getUserId() : String {
    val auth = Firebase.auth
    return requireNotNull(auth.currentUser?.uid)
}

