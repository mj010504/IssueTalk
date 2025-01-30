package com.example.issuetalk.core.data.repository.auth

import com.example.issuetalk.core.domain.repository.auth.AuthRepository
import com.example.issuetalk.core.network.source.auth.AuthDataSource
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource
) : AuthRepository {
    override suspend fun loginFirebase(email: String, password: String): Result<Unit> =
        authDataSource.loginFirebase(email, password)

    override suspend fun signUpFirebase(
        email: String,
        password: String,
        name: String
    ): Result<Unit> = authDataSource.signUpFirebase(email, password, name)

}
