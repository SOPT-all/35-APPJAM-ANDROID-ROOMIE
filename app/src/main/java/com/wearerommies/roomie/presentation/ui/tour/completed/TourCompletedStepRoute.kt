package com.wearerommies.roomie.presentation.ui.tour.completed

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.wearerommies.roomie.R
import com.wearerommies.roomie.presentation.core.component.RoomieButton
import com.wearerommies.roomie.ui.theme.RoomieAndroidTheme
import com.wearerommies.roomie.ui.theme.RoomieTheme

@Composable
fun TourCompletedStepRoute(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateToMap: () -> Unit,
    navigateToHome: () -> Unit,
    viewModel: TourCompletedStepViewModel = hiltViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                TourCompletedSideEffect.NavigateUp -> navigateUp()

                is TourCompletedSideEffect.NavigateToMap -> navigateToMap()

                is TourCompletedSideEffect.NavigateToHome -> navigateToHome()
            }
        }
    }

    TourCompletedStepScreen(
        paddingValues = paddingValues,
        navigateUp = viewModel::navigateUp,
        navigateToHome = viewModel::navigateHome,
        navigateToMap = viewModel::navigateToMap
    )
}

@Composable
fun TourCompletedStepScreen(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToMap: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(RoomieTheme.colors.grayScale1)
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height((LocalConfiguration.current.screenHeightDp * 0.235).dp))

        Text(
            text = stringResource(R.string.tour_apply_complete),
            style = RoomieTheme.typography.heading2Sb20,
            color = RoomieTheme.colors.grayScale12
        )

        Spacer(Modifier.height(2.dp))

        Text(
            text = stringResource(R.string.tour_apply_complete_kakaotalk),
            style = RoomieTheme.typography.body4R12,
            color = RoomieTheme.colors.grayScale8
        )

        Spacer(Modifier.height(8.dp))

        Image(
            painter = painterResource(R.drawable.img_tour_apply_complete),
            contentDescription = null,
            modifier = Modifier
                .size((LocalConfiguration.current.screenWidthDp * 0.6).dp)
        )

        Spacer(Modifier.weight(1f))

        Row(
            modifier = Modifier
                .padding(
                    horizontal = 16.dp,
                    vertical = 12.dp
                ),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            RoomieButton(
                text = stringResource(R.string.tour_other_room),
                backgroundColor = RoomieTheme.colors.grayScale1,
                textColor = RoomieTheme.colors.grayScale8,
                onClick = navigateToMap,
                modifier = Modifier
                    .weight(0.4613F),
                borderColor = RoomieTheme.colors.grayScale6,
                borderWidth = 1.dp
            )
            RoomieButton(
                text = stringResource(R.string.tour_completed),
                backgroundColor = RoomieTheme.colors.primary,
                textColor = RoomieTheme.colors.grayScale1,
                onClick = navigateToHome,
                modifier = Modifier
                    .weight(0.4F),
                isPressed = true,
                pressedColor = RoomieTheme.colors.primaryLight1
            )
        }
    }
}

@Preview
@Composable
fun TourCompletedStepScreenPreview() {
    RoomieAndroidTheme {
        TourCompletedStepScreen(
            paddingValues = PaddingValues(0.dp),
            navigateUp = {},
            navigateToHome = {},
            navigateToMap = {}
        )
    }
}
