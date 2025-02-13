package com.example.issuetalk.core.data.repository.auth

import com.example.issuetalk.core.domain.repository.auth.AuthRepository
import com.example.issuetalk.core.network.source.auth.AuthDataSource
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource
) : AuthRepository {
    override suspend fun loginFirebaseWithKakao(idToken : String): Result<Unit> = authDataSource.loginFirebaseWithKakao(idToken)

    override suspend fun signUp(): Result<Unit> = authDataSource.signUp()

}
