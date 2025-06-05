package com.wearerommies.roomie

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class Roomie : Application() {
    override fun onCreate() {
        super.onCreate()

        setTimber()
        setDarkMode()
        setKakaoSdk()
    }

    private fun setKakaoSdk() {
        val keyHash = Utility.getKeyHash(this)
        Timber.tag("keyHash").d(keyHash)
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_KEY)
    }

    private fun setTimber() {
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    private fun setDarkMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}
