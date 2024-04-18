package com.example.kittyviewer.data.repository

import com.example.kittyviewer.data.model.Kitty
import com.example.kittyviewer.data.result.ApiResult
import com.example.kittyviewer.database.dao.KittyDao
import com.example.kittyviewer.database.model.KittyEntity
import com.example.kittyviewer.database.model.asExternalModel
import com.example.kittyviewer.database.model.toDbEntity
import com.example.kittyviewer.network.NetworkDataSource
import com.example.kittyviewer.network.model.RemoteKitty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val kittyDao: KittyDao,
    private val dataSource: NetworkDataSource,
) : Repository {
    override fun getKittiesList(): Flow<List<Kitty>> {
        return kittyDao.getKittiesList().map {
            it.map(KittyEntity::asExternalModel)
        }
    }

    override suspend fun syncKitties(page: Int): Flow<ApiResult> {
        return flow {
            emit(ApiResult.ApiLoading)
            dataSource.getKittiesList(page)
                .onSuccess {
                    withContext(Dispatchers.IO) {
                        kittyDao.updateKitties(
                            it.map(RemoteKitty::toDbEntity),
                            page == 1
                        )
                    }
                    emit(ApiResult.ApiSuccess)
                }
                .onFailure {
                    emit(ApiResult.ApiError(it.localizedMessage))
                }
        }
    }
}