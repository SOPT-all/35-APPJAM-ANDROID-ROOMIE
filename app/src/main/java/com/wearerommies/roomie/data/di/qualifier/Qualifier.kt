package com.wearerommies.roomie.data.di.qualifier

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NoToken

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class JWT