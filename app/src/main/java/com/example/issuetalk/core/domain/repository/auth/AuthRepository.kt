package com.example.issuetalk.core.domain.repository.auth

interface AuthRepository {
    suspend fun signUp() : Result<Unit>
}