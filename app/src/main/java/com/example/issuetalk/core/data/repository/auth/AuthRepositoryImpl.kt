package com.example.issuetalk.core.data.repository.auth

import UserInformationRequest
import UserInformationResponse
import com.example.issuetalk.core.domain.repository.auth.AuthRepository
import com.example.issuetalk.core.network.source.auth.AuthDataSource
import com.example.issuetalk.core.network.source.auth.LocalAuthDataSource
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val localDataSource: LocalAuthDataSource
) : AuthRepository {
    override suspend fun loginFirebaseWithKakao(idToken: String): Result<Unit> =
        authDataSource.loginFirebaseWithKakao(idToken)

    override suspend fun verifyRegistered(): Result<Boolean> = authDataSource.verifyRegistered()

    override suspend fun getUserInformation(): Result<UserInformationResponse> =
        authDataSource.getUserInformation()

    override suspend fun setUserInformation(userInformationRequest: UserInformationRequest): Result<Unit> =
        authDataSource.setUserInformation(userInformationRequest)

    override suspend fun deleteUserInformation(): Result<Unit> =
        authDataSource.deleteUserInformation()

    override fun saveShowHome() {
        localDataSource.saveShowHome()
    }

    override fun getShowHome() = localDataSource.getShowHome()

    override fun clearShowHome() = localDataSource.clearShowHome()

}
