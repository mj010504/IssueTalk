package com.example.issuetalk.feature.auth


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.issuetalk.feature.auth.LoginViewModel.LoginEvent
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.issuetalk.R
import com.example.issuetalk.core.common.util.clickWithRipple
import com.example.issuetalk.core.designsystem.component.checkDialog
import com.example.issuetalk.core.designsystem.theme.primaryColor
import com.example.issuetalk.core.designsystem.theme.subColor
import com.example.issuetalk.feature.post.writepost.WritePostScreen


@Composable
fun LoginRoute(
    navigateToHome: () -> Unit,
    navigateToSignUp: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    var dialogMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(true) {
        viewModel.eventChannel.collect { event ->
            when (event) {
                is LoginEvent.NavigateToHome -> navigateToHome()
                is LoginEvent.NavigateToSignUp -> navigateToSignUp()
                is LoginEvent.ShowDialog -> dialogMessage = event.message
            }
        }
    }

    LoginScreen(
        loginKakao = viewModel::loginKakao,
        navigateToSignUp = viewModel::navigateToSignUp,
        navigateToHome = viewModel::navigateToHome
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
    loginKakao: () -> Unit,
    navigateToSignUp: () -> Unit,
    navigateToHome: () -> Unit,
) {


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
        Text("같은 이슈, 다른 생각, 새로운 시각", style = TextStyle(fontSize = 17.sp, fontWeight = FontWeight.SemiBold))
        Spacer(modifier = Modifier.height(60.dp))
        Image(
            painter = painterResource(R.drawable.kakao_login),
            contentDescription = "카카오 로그인",
            modifier = Modifier.clickWithRipple {
                loginKakao()
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


@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    LoginScreen({}, {}, {})
}

