package com.wearerommies.roomie.data.datalocal.datasource

interface TokenDataSource {
    var accessToken: String
    var refreshToken: String
    fun clearInfo()
}