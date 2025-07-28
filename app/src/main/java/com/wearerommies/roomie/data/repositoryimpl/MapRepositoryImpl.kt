package com.wearerommies.roomie.data.repositoryimpl

import com.wearerommies.roomie.data.datasource.MapDataSource
import com.wearerommies.roomie.data.dto.request.toDto
import com.wearerommies.roomie.domain.entity.FilterEntity
import com.wearerommies.roomie.domain.entity.FilterResultWrapperEntity
import com.wearerommies.roomie.domain.entity.SearchResultEntity
import com.wearerommies.roomie.domain.repository.MapRepository
import javax.inject.Inject

internal class MapRepositoryImpl @Inject constructor(
    private val mapDataSource: MapDataSource
) : MapRepository {
    override suspend fun getSearchResult(query: String): Result<List<SearchResultEntity>> =
        runCatching {
            mapDataSource.getSearchResult(query = query).data.toEntity()
        }

    override suspend fun getFilterResult(filter: FilterEntity): Result<FilterResultWrapperEntity> =
        runCatching {
           val response =  mapDataSource.getFilterResult(filter = filter.toDto())

            FilterResultWrapperEntity(
                code = response.code,
                message = response.message,
                result = response.data.toEntity()
            )

        }
}
