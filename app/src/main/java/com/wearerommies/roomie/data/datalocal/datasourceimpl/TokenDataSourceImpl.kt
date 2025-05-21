package com.wearerommies.roomie.data.datalocal.datasourceimpl

import android.content.SharedPreferences
import androidx.core.content.edit
import com.wearerommies.roomie.data.datalocal.datasource.TokenDataSource
import javax.inject.Inject

class TokenDataSourceImpl @Inject constructor(
    private val tokenDataSource: SharedPreferences,
) : TokenDataSource {
    override var accessToken: String
        get() = tokenDataSource.getString(ACCESS_TOKEN, "") ?: ""
        set(value) = tokenDataSource.edit { putString(ACCESS_TOKEN, value) }

    override var refreshToken: String
        get() = tokenDataSource.getString(REFRESH_TOKEN, "") ?: ""
        set(value) = tokenDataSource.edit { putString(REFRESH_TOKEN, value) }

    override fun clearInfo() {
        tokenDataSource.edit().clear().commit()
    }

    companion object {
        private const val ACCESS_TOKEN = "ACCESS_TOKEN"
        private const val REFRESH_TOKEN = "REFRESH_TOKEN"
    }
}