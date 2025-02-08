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


    private val _nameText = MutableStateFlow("")
    val nameText = _nameText.asStateFlow()

    private val _isNameValid = MutableStateFlow(false)
    val isNameValid = _isNameValid.asStateFlow()

    private val _gender : MutableStateFlow<Gender?> = MutableStateFlow(null)
    val gender = _gender.asStateFlow()

    private val _birthYear = MutableStateFlow("")
    val birthYear = _birthYear.asStateFlow()

    fun setNameText(nameText: String) {
        _nameText.value = nameText
        validateName()
    }

    fun setGender(gender : Gender) {
        _gender.value = gender
    }

    fun setBirthYear(birthYear : String) {
        _birthYear.value = birthYear;
    }

    private fun validateName() {
        val nicknameRegex = "^.{$NAME_MIN_LENGTH,$NAME_MAX_LENGTH}$".toRegex()
         _isNameValid.value = _nameText.value.trim().matches(nicknameRegex)
    }


    fun signUp() = viewModelScope.launch {

    }

    sealed class SignUpEvent {
        data object  NavigateToHome : SignUpEvent()
        data class ShowDialog(val message: String) : SignUpEvent()
    }

    companion object {
        private const val NAME_MIN_LENGTH = 1
        private const val NAME_MAX_LENGTH = 12
        private const val SIGNUP_ERROR = "회원가입에 실패했습니다"
    }

    enum class Gender(val gender: String) {
      MALE("남자"), FEMALE("여자")
    }


}