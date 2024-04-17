package com.example.kittyviewer.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.kittyviewer.database.dao.KittyDao
import com.example.kittyviewer.database.model.KittyEntity

@Database(
    entities = [
        KittyEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class KittyCache : RoomDatabase() {
    abstract fun kittyDao(): KittyDao
}