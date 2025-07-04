package com.wearerommies.roomie.presentation.ui.onboarding.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wearerommies.roomie.R
import com.wearerommies.roomie.ui.theme.RoomieAndroidTheme
import com.wearerommies.roomie.ui.theme.RoomieTheme

@Composable
internal fun OnboardingPagerItem(
    @StringRes title: Int,
    @StringRes description: Int,
    @DrawableRes image: Int,
    screenWeight: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(title),
            color = RoomieTheme.colors.primary,
            style = RoomieTheme.typography.obdTitle2Sb22
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            text = stringResource(description),
            color = RoomieTheme.colors.grayScale8,
            style = RoomieTheme.typography.body3M14,
            textAlign = TextAlign.Center
        )

        Image(
            modifier = Modifier
                .width(if (image == R.drawable.img_onboarding3) screenWeight.dp else (screenWeight * 0.667f).dp)
                .padding(top = if (image == R.drawable.img_onboarding3) 24.dp else 68.dp),
            painter = painterResource(image),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}

@Preview
@Composable
fun PagerSampleItemPreview() {
    RoomieAndroidTheme {
        OnboardingPagerItem(
            screenWeight = 360,
            title = R.string.onboarding_title1,
            description = R.string.onboarding_description1,
            image = R.drawable.img_home_character
        )
    }
}
