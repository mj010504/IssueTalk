package com.example.issuetalk.feature.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.issuetalk.core.domain.repository.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _eventChannel = Channel<SignUpEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    private val _emailText = MutableStateFlow("")
    val emailText = _emailText.asStateFlow()

    private val _nameText = MutableStateFlow("")
    val nameText = _nameText.asStateFlow()

    private val _passwordText = MutableStateFlow("")
    val passwordText = _passwordText.asStateFlow()

    private val _passwordCheckText = MutableStateFlow("")
    val passwordCheckText = _passwordCheckText.asStateFlow()

    private val _isNameValid = MutableStateFlow(false)
    val isNameValid = _isNameValid.asStateFlow()

    private val _isEmailValid = MutableStateFlow(false)
    val isEmailValid = _isEmailValid.asStateFlow()

    private val _isPasswordValid = MutableStateFlow(false)
     val isPasswordValid = _isPasswordValid.asStateFlow()

    private val _isPasswordMatch = MutableStateFlow(false)
    val isPasswordMatch = _isPasswordMatch.asStateFlow()

    fun setEmailText(emailText: String) {
        _emailText.value = emailText
        validateEmail()
    }

    fun setNameText(nameText: String) {
        _nameText.value = nameText
        validateName()
    }

    fun setPasswordText(passwordText: String) {
        _passwordText.value = passwordText
        validatePassword()
        validatePasswordMatch()
    }

    fun setPasswordCheckText(passwordCheckText: String) {
        _passwordCheckText.value = passwordCheckText
        validatePasswordMatch()
    }

    private fun validateName() {
        val nicknameRegex = "^.{$NAME_MIN_LENGTH,$NAME_MAX_LENGTH}$".toRegex()
         _isNameValid.value = _nameText.value.trim().matches(nicknameRegex)
    }

    private fun validateEmail( ) {
        val emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
        _isEmailValid.value =  _emailText.value.trim().matches(emailRegex.toRegex())
    }

    private fun validatePassword() {
        val regex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[\\W_]).{$PASSWORD_MIN_LENGTH,$PASSWORD_MAX_LENGTH}$"
        _isPasswordValid.value = _passwordText.value.trim().matches(Regex(regex))
    }

    private fun validatePasswordMatch() {
        _isPasswordMatch.value = _passwordText.value.trim() == _passwordCheckText.value.trim()
    }

    fun signUpFirebase() = viewModelScope.launch {
        authRepository.signUpFirebase(
            _emailText.value.trim(),
            _passwordText.value.trim(),
            _nameText.value.trim()
        ).onSuccess {
            _eventChannel.send(SignUpEvent.NavigateToWelcome)
        }.onFailure {
            _eventChannel.send(SignUpEvent.ShowDialog(SIGNUP_ERROR))
        }
    }

    sealed class SignUpEvent {
        data object NavigateToWelcome : SignUpEvent()
        data class ShowDialog(val message: String) : SignUpEvent()
    }

    companion object {
        private const val NAME_MIN_LENGTH = 1
        private const val NAME_MAX_LENGTH = 12
        private const val PASSWORD_MIN_LENGTH = 8
        private const val PASSWORD_MAX_LENGTH = 16
        private const val SIGNUP_ERROR = "회원가입에 실패했습니다"
        private const val DUPLICATED_EMAIL = "이미 존재하는 이메일 입니다"
    }


}