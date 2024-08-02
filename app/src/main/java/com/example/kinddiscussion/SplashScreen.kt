package com.example.kinddiscussion


import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.kinddiscussion.Home.HomeScreen
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(navController: NavController) {
    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser

    LaunchedEffect(Unit) {
        delay(500)
        val nextRoute = if(user != null) "home" else "login"
        navController.navigate(nextRoute) {
            popUpTo("splash") { inclusive = true }
        }
    }


    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Icon(painter =  painterResource(id = R.drawable.issuetalk), contentDescription = null,
            tint =  Color.Unspecified)
        Text(text = stringResource(R.string.app_name), modifier = Modifier.padding(top = 45.dp),
            style = TextStyle(fontSize = 50.sp, fontFamily= FontFamily(Font(R.font.app_title )))
        )
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewTextFielsddExample() {
    val navController = rememberNavController()
    val isUser = true
    SplashScreen(navController)
}
