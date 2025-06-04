package com.wearerommies.roomie.presentation.ui.onboarding

import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wearerommies.roomie.R
import com.wearerommies.roomie.presentation.core.component.RoomieButton
import com.wearerommies.roomie.presentation.ui.onboarding.component.OnboardingPagerItem
import com.wearerommies.roomie.ui.theme.RoomieAndroidTheme
import com.wearerommies.roomie.ui.theme.RoomieTheme
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun OnboardingRoute(
    paddingValues: PaddingValues,
    navigateToLogin: () -> Unit,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    OnboardingScreen(
        paddingValues = paddingValues,
        navigateToLogin = navigateToLogin,
        pages = viewModel.pages
    )
}

@Composable
fun OnboardingScreen(
    paddingValues: PaddingValues,
    navigateToLogin: () -> Unit,
    pages: PersistentList<Triple<Int, Int, Int>>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(RoomieTheme.colors.grayScale1)
            .padding(bottom = paddingValues.calculateBottomPadding()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val screenWidth = LocalConfiguration.current.screenWidthDp
        val screenHeight = LocalConfiguration.current.screenHeightDp

        val pageCount = 3
        val pagerState = rememberPagerState(pageCount = { pageCount })

        Spacer(
            modifier = Modifier
                .height((screenHeight * 0.13).dp),
        )

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(pageCount) { index ->
                val isSelected = pagerState.currentPage == index
                val animatedWidth by animateFloatAsState(
                    targetValue = if (isSelected) 16f else 8f,
                    animationSpec = tween(durationMillis = 300, easing = EaseInOut),
                )

                Box(
                    modifier = Modifier
                        .width(animatedWidth.dp)
                        .height(8.dp)
                        .clip(RoundedCornerShape(108.dp))
                        .background(if (isSelected) RoomieTheme.colors.primary else RoomieTheme.colors.grayScale5)
                )

                if (index != pageCount - 1) {
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }

        Spacer(
            modifier = Modifier.height(58.dp)
        )

        HorizontalPager(
            state = pagerState
        ) { page ->
            val (title, description, image) = pages[page]

            OnboardingPagerItem(
                screenWeight = screenWidth,
                title = title,
                description = description,
                image = image,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(
            modifier = Modifier.weight(1f)
        )

        RoomieButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 12.dp
                ),
            text = stringResource(R.string.start_app),
            backgroundColor = RoomieTheme.colors.primary,
            textColor = RoomieTheme.colors.grayScale1,
            onClick = navigateToLogin
        )

        Spacer(
            modifier = Modifier.height((screenHeight * 0.038).dp),
        )
    }
}

@Preview
@Composable
fun OnboardingScreenPreview() {
    RoomieAndroidTheme {
        OnboardingScreen(
            paddingValues = PaddingValues(),
            navigateToLogin = {},
            pages = persistentListOf(
                Triple(
                    R.string.onboarding_title1,
                    R.string.onboarding_description1,
                    R.drawable.img_home_character
                ),
                Triple(
                    R.string.onboarding_title2,
                    R.string.onboarding_description2,
                    R.drawable.img_home_character
                ),
                Triple(
                    R.string.onboarding_title3,
                    R.string.onboarding_description3,
                    R.drawable.img_home_character
                )
            )
        )
    }
}
