package com.example.issuetalk.feature.splash


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.issuetalk.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Composable
 fun SplashRoute(
    navigateToLogin: () -> Unit,
    navigateToHome: () -> Unit,
) {
    SplashScreen(navigateToLogin, navigateToHome)
}

@Composable
fun SplashScreen(
    navigateToLogin: () -> Unit,
    navigateToHome: () -> Unit,
) {
    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser

    LaunchedEffect(true) {
        delay(500)
        navigateToLogin()
//        if(user == null) navigateToLogin()
//        else navigateToHome()
    }

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

