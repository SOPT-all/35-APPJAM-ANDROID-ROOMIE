package com.wearerommies.roomie.domain.repository

import com.wearerommies.roomie.domain.entity.FilterEntity
import com.wearerommies.roomie.domain.entity.FilterResultWrapperEntity
import com.wearerommies.roomie.domain.entity.SearchResultEntity

interface MapRepository {
    suspend fun getSearchResult(query:String):Result<List<SearchResultEntity>>

    suspend fun getFilterResult(filter:FilterEntity):Result<FilterResultWrapperEntity>
}
