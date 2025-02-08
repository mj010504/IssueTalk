package com.example.issuetalk.core.data.repository.auth

import com.example.issuetalk.core.domain.repository.auth.AuthRepository
import com.example.issuetalk.core.network.source.auth.AuthDataSource
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource
) : AuthRepository {
    override suspend fun loginKakao(): Result<Unit> = authDataSource.loginKakao()
    override suspend fun signUp(): Result<Unit> = authDataSource.signUp()


}
