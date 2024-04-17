package com.example.kittyviewer.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.kittyviewer.data.model.Kitty
import com.example.kittyviewer.network.model.RemoteKitty

@Entity(
    tableName = "kitties"
)
data class KittyEntity(
    @PrimaryKey
    val id: String,
    val url: String,
    val width: Int,
    val height: Int,
    val createdAt: Long = System.currentTimeMillis()
)

fun KittyEntity.asExternalModel() = Kitty(
    id = id,
    url = url,
    width = width,
    height = height
)

fun RemoteKitty.toDbEntity() = KittyEntity(
    id = id,
    url = url ?: "",
    width = width ?: 0,
    height = height ?: 0
)