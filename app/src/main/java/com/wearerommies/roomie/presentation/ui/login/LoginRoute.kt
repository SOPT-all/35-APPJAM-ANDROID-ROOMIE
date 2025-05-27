package com.wearerommies.roomie.presentation.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.wearerommies.roomie.R
import com.wearerommies.roomie.presentation.core.extension.noRippleClickable
import com.wearerommies.roomie.ui.theme.RoomieAndroidTheme
import com.wearerommies.roomie.ui.theme.RoomieTheme

@Composable
fun LoginRoute(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateToHome: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is LoginSideEffect.StartLogin -> {
                        // 필요 시 StartLogin 추가 처리
                    }

                    is LoginSideEffect.LoginSuccess -> {
                        navigateToHome()
                    }

                    is LoginSideEffect.LoginError -> {
                        // 필요 시 LoginError 추가 처리
                    }
                }
            }
    }

    LoginScreen(
        paddingValues = paddingValues,
        navigateUp = navigateUp,
        state = state,
        onLoginClick = { viewModel.startKakaoLogin(context) },
    )

}

@Composable
fun LoginScreen(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    state: LoginState,
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(RoomieTheme.colors.grayScale1)
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            modifier = Modifier
                .padding(top = 105.dp),
            imageVector = ImageVector.vectorResource(R.drawable.ic_roomie_logo),
            contentDescription = stringResource(R.string.roomie_logo),
            tint = Color.Unspecified
        )
        Text(
            modifier = Modifier
                .padding(top = 20.dp),
            text = stringResource(R.string.login_title),
            style = RoomieTheme.typography.title2Sb16,
            color = RoomieTheme.colors.gradientPrimary
        )

        //todo: image 추가 예정

        Spacer(
            modifier = Modifier
                .weight(1f)
        )

        Image(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, bottom = 42.dp)
                .noRippleClickable { onLoginClick() },
            painter = painterResource(R.drawable.img_kakao_login),
            contentDescription = stringResource(R.string.kakao_login),
            contentScale = ContentScale.Crop
        )
    }

}

@Preview
@Composable
fun LoginScreenPreview() {
    RoomieAndroidTheme {
        LoginScreen(
            paddingValues = PaddingValues(),
            navigateUp = {},
            state = LoginState(),
            onLoginClick = {},
        )
    }
}