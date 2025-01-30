package com.example.issuetalk.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.issuetalk.core.domain.repository.auth.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _eventChannel = Channel<LoginEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    private val _emailText = MutableStateFlow("")
    val emailText = _emailText.asStateFlow()

    private val _passwordText = MutableStateFlow("")
    val passwordText = _passwordText.asStateFlow()

    fun setEmailText(emailText: String) {
        _emailText.value = emailText
    }

    fun setPasswordText(passwordText: String) {
        _passwordText.value = passwordText
    }

    fun loginFirebase() = viewModelScope.launch {
            if (_emailText.value.trim().isEmpty() || _passwordText.value.trim().isEmpty()) {
                _eventChannel.send(LoginEvent.ShowDialog(LOGIN_FIELD_EMPTY))
                return@launch
            }

            authRepository.loginFirebase(
                emailText.value.trim(),
                passwordText.value.trim()
            ).onSuccess {
                _eventChannel.send(LoginEvent.NavigateToHome)
            }.onFailure {
                _eventChannel.send(LoginEvent.ShowDialog(LOGIN_ERROR))
            }

        }


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

    sealed class LoginEvent {
        data object NavigateToSignUp : LoginEvent()
        data object NavigateToHome : LoginEvent()
        data class ShowDialog(val message: String) : LoginEvent()
    }

    companion object {
        private const val LOGIN_FIELD_EMPTY = "아이디와 비밀번호를 입력해주세요."
        private const val LOGIN_ERROR = "아이디와 비밀번호가 일치하지 않습니다."
    }


}



