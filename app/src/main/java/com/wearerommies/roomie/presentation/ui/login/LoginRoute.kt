package com.wearerommies.roomie.presentation.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
    //todo: navigateToOnboarding: () -> Unit,
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
                        if (sideEffect.isRegistered) {
                            navigateToHome()
                        } else {
                            //todo: navigateToOnboarding()
                        }
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
    val screenWeigth = LocalConfiguration.current.screenWidthDp
    val height = (screenWeigth * 0.5).dp

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(RoomieTheme.colors.grayScale1)
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .noRippleClickable { onLoginClick() },
            painter = painterResource(R.drawable.img_profile),
            contentDescription = stringResource(R.string.profile_image),
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