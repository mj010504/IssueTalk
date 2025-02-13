package com.example.issuetalk.feature.auth

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.issuetalk.core.domain.repository.auth.AuthRepository
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository

) : ViewModel() {
    private val _eventChannel = Channel<LoginEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    fun navigateToSignUp() {
        viewModelScope.launch {
            _eventChannel.send(LoginEvent.NavigateToSignUp)
        }
    }

    fun navigateToHome() {
        viewModelScope.launch {
            _eventChannel.send(LoginEvent.NavigateToHome)
        }
    }

      fun loginFirebaseWithKakao(idToken: String) = viewModelScope.launch {
        authRepository.loginFirebaseWithKakao(idToken)
            .onSuccess {
                verifyResitered()
            }.onFailure {
                _eventChannel.send(LoginEvent.ShowDialog(LOGIN_ERROR))
            }
    }

    private suspend fun verifyResitered() = viewModelScope.launch {
        authRepository.verifyRegistered()
            .onSuccess { isRegistered ->
                if(isRegistered) {
                    _eventChannel.send(LoginEvent.NavigateToHome)
                    return@onSuccess
                }

                _eventChannel.send(LoginEvent.NavigateToSignUp)

            }.onFailure {
                _eventChannel.send(LoginEvent.ShowDialog(LOGIN_ERROR))
            }
    }

    sealed class LoginEvent {
        data object NavigateToSignUp : LoginEvent()
        data object NavigateToHome : LoginEvent()
        data class ShowDialog(val message: String) : LoginEvent()
    }

    companion object {
        private const val LOGIN_ERROR = "로그인에 실패했습니다."
    }


}



