package com.example.issuetalk.core.domain.repository.auth

interface AuthRepository {
    suspend fun loginKakao() : Result<Unit>
    suspend fun signUp() : Result<Unit>
}