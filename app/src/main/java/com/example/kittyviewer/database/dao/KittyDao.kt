package com.example.kittyviewer.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.kittyviewer.database.model.KittyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface KittyDao {
    @Query(
        value = "SELECT * FROM kitties ORDER BY createdAt Asc"
    )
    fun getKittiesList(): Flow<List<KittyEntity>>

    @Upsert
    fun upsertKitties(entities: List<KittyEntity>)

    @Query(
        value = "DELETE FROM kitties"
    )
    fun clearKitties()

    @Transaction
    fun updateKitties(entities: List<KittyEntity>, shouldReset: Boolean) {
        if (shouldReset)
            clearKitties()
        upsertKitties(entities)
    }
}