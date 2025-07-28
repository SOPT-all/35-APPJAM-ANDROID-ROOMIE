package com.wearerommies.roomie.presentation.core.util

import com.naver.maps.geometry.LatLngBounds

fun LatLngBounds.isSameAs(other: LatLngBounds?): Boolean {
    return this.southWest.latitude == other?.southWest?.latitude &&
            this.southWest.longitude == other.southWest.longitude &&
            this.northEast.latitude == other.northEast.latitude &&
            this.northEast.longitude == other.northEast.longitude
}