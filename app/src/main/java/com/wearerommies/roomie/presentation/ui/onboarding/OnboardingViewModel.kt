package com.wearerommies.roomie.presentation.ui.onboarding

import androidx.lifecycle.ViewModel
import com.wearerommies.roomie.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(

) : ViewModel() {
    val pages = persistentListOf(
        Triple(
            R.string.onboarding_title1,
            R.string.onboarding_description1,
            R.drawable.img_home_character // 그래픽은 아직 미정
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
        ),
    )
}
