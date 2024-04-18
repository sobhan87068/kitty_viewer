package com.example.kittyviewer.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kittyviewer.database.model.BookmarkEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao {
    @Query(
        value = "SELECT * FROM bookmarks ORDER BY createdAt Asc"
    )
    fun getBookmarksList(): Flow<List<BookmarkEntity>>

    @Query(
        value = "SELECT EXISTS(SELECT * FROM bookmarks WHERE id = :id)"
    )
    fun isBookmarked(id: String): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addToBookmarks(bookmarkEntity: BookmarkEntity)

    @Delete
    fun deleteFromBookmarks(vararg bookmarkEntity: BookmarkEntity)
}