package com.example.issuetalk.feature.splash

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.issuetalk.core.domain.repository.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _eventChannel = Channel<SplashEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

     fun checkShowHome() = viewModelScope.launch {
        val showHome = authRepository.getShowHome()
        if(showHome) _eventChannel.send(SplashEvent.NavigateToHome)
        else _eventChannel.send(SplashEvent.NavigateToLogin)
    }

    sealed class SplashEvent {
        data object NavigateToLogin : SplashEvent()
        data object NavigateToHome : SplashEvent()
    }
}