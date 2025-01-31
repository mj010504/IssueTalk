package com.example.issuetalk.core.domain.repository.auth

interface AuthRepository {
    suspend fun loginFirebase(email: String, password: String) : Result<Unit>
    suspend fun signUpFirebase(email: String, password: String, name: String) : Result<Unit>
}