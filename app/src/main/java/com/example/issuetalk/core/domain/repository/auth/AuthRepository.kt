package com.example.issuetalk.core.domain.repository.auth

import UserInformationRequest
import UserInformationResponse


interface AuthRepository {
    suspend fun loginFirebaseWithKakao(idToken : String) : Result<Unit>
    suspend fun verifyRegistered() : Result<Boolean>
    suspend fun getUserInformation() : Result<UserInformationResponse>
    suspend fun setUserInformation(userInformationRequest: UserInformationRequest) : Result<Unit>
    suspend fun deleteUserInformation() : Result<Unit>
}