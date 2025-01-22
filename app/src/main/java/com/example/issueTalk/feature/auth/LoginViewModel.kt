package com.example.issueTalk.feature.auth

import android.app.appsearch.SearchResult
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


class LoginViewModel (

) : ViewModel() {
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

    fun login() {
        val auth = FirebaseAuth.getInstance()

        if (_emailText.value == "" || _passwordText.value == "") {
//            showNotTextDialog = true
        } else {
            auth.signInWithEmailAndPassword(emailText.value, passwordText.value)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
//                                navController.navigate("home")
                    } else {
//                        showLoginFailedDialog = true
                    }
                }

        }
    }


}