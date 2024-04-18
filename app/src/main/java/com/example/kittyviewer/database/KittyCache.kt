package com.example.kittyviewer.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.kittyviewer.database.dao.BookmarkDao
import com.example.kittyviewer.database.dao.KittyDao
import com.example.kittyviewer.database.model.BookmarkEntity
import com.example.kittyviewer.database.model.KittyEntity


@Database(
    entities = [
        KittyEntity::class,
        BookmarkEntity::class
    ],
    version = 2,
    exportSchema = true,
)
abstract class KittyCache : RoomDatabase() {
    abstract fun kittyDao(): KittyDao
    abstract fun bookmarkDao(): BookmarkDao

    companion object {
        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // create comic bookmark table
                database.execSQL(
                    "CREATE TABLE `bookmarks` (`id` TEXT NOT NULL, `url` TEXT NOT NULL, " +
                            "`width` INTEGER NOT NULL, `height` INTEGER NOT NULL, " +
                            "`createdAt` INTEGER NOT NULL, PRIMARY KEY(`id`))"
                )
            }
        }
    }
}