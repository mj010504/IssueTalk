package com.example.issuetalk.feature.auth


import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.issuetalk.feature.auth.LoginViewModel.LoginEvent
import com.example.issuetalk.R
import com.example.issuetalk.core.common.util.clickWithRipple
import com.example.issuetalk.core.designsystem.component.checkDialog
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient


@Composable
fun LoginRoute(
    navigateToHome: () -> Unit,
    navigateToSignUp: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    var dialogMessage by remember { mutableStateOf<String?>(null) }

    fun showDialog(message: String) {
        dialogMessage = message
    }

    LaunchedEffect(true) {
        viewModel.eventChannel.collect { event ->
            when (event) {
                is LoginEvent.NavigateToHome -> navigateToHome()
                is LoginEvent.NavigateToSignUp -> navigateToSignUp()
                is LoginEvent.LoginError -> dialogMessage = event.message
            }
        }
    }

    LoginScreen(
        loginFirebaseWithKakao = viewModel::loginFirebaseWithKakao,
        navigateToHome = viewModel::navigateToHome,
        showDialog = ::showDialog
    )

    dialogMessage?.let {
        checkDialog(
            onDismiss = { dialogMessage = null },
            dialogText = it
        )
    }
}

@Composable
fun LoginScreen(
    loginFirebaseWithKakao: (String) -> Unit,

    navigateToHome: () -> Unit,
    showDialog: (String) -> Unit
) {
    val context = LocalContext.current


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.issuetalk),
            contentDescription = "앱 아이콘",
            modifier = Modifier
                .width(140.dp)
                .height(210.dp),
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = stringResource(R.string.app_name),
            style = TextStyle(fontSize = 48.sp, fontFamily = FontFamily(Font(R.font.app_title)))
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            "같은 이슈, 다른 생각, 새로운 시각",
            style = TextStyle(fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
        )
        Spacer(modifier = Modifier.height(60.dp))
        Image(
            painter = painterResource(R.drawable.kakao_login),
            contentDescription = "카카오 로그인",
            modifier = Modifier.clickWithRipple {
                loginKakao(context, showDialog, loginFirebaseWithKakao)
            }
        )
        Spacer(Modifier.height(24.dp))
        Text(
            "둘러보기",
            color = Color.Black,
            style = TextStyle(fontSize = 16.sp),
            modifier = Modifier.clickWithRipple {
                navigateToHome()
            }

        )
        Spacer(Modifier.weight(1f))
    }


}


private fun loginKakao(
    context: Context,
    showDialog: (String) -> Unit,
    loginFirebaseWithKakao: (String) -> Unit
) {
    val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            showDialog("로그인에 실패했습니다")
        } else if (token != null) {
            loginFirebaseWithKakao(token.idToken!!)
        }

    }

    if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
        UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
            if (error != null) {
                showDialog("로그인에 실패했습니다.")
                // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                    return@loginWithKakaoTalk
                }

                // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
            } else if (token != null) {
                loginFirebaseWithKakao(token.idToken!!)
            }
        }
    } else {
        UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
    }
}


//
//@Preview(showBackground = true)
//@Composable
//fun LoginPreview() {
//    LoginScreen({}, {})
//}

