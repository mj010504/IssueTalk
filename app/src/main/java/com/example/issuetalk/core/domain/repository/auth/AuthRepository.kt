package com.example.issuetalk.core.domain.repository.auth

interface AuthRepository {
    suspend fun loginFirebaseWithKakao(idToken : String) : Result<Unit>
    suspend fun signUp() : Result<Unit>
}