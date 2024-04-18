package com.example.kittyviewer.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.kittyviewer.data.model.Kitty

@Entity(
    tableName = "bookmarks"
)
data class BookmarkEntity(
    @PrimaryKey
    val id: String,
    val url: String,
    val width: Int,
    val height: Int,
    val createdAt: Long = System.currentTimeMillis()
)

fun BookmarkEntity.asExternalModel() = Kitty(
    id = id,
    url = url,
    width = width,
    height = height
)

fun Kitty.asBookmarkEntity() = BookmarkEntity(
    id = id,
    url = url,
    width = width,
    height = height
)