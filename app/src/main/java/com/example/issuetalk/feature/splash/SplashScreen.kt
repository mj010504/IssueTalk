package com.example.issuetalk.feature.splash


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.issuetalk.R
import com.example.issuetalk.feature.auth.LoginViewModel
import com.example.issuetalk.feature.auth.LoginViewModel.LoginEvent
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Composable
 fun SplashRoute(
    navigateToLogin: () -> Unit,
    navigateToHome: () -> Unit,
    viewModel: SplashViewModel = hiltViewModel()
) {
    LaunchedEffect(true) {
        viewModel.checkShowHome()
        viewModel.eventChannel.collect { event ->
            when (event) {
                is SplashViewModel.SplashEvent.NavigateToHome -> navigateToHome()
                is SplashViewModel.SplashEvent.NavigateToLogin -> navigateToLogin()
            }
        }
    }

    SplashScreen()
}

@Composable
fun SplashScreen(

) {



    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(painter =  painterResource(id = R.drawable.issuetalk), contentDescription = null)
        Text(text = stringResource(R.string.app_name), modifier = Modifier.padding(top = 45.dp),
            style = TextStyle(fontSize = 50.sp, fontFamily= FontFamily(Font(R.font.app_title)))
        )
    }
}

