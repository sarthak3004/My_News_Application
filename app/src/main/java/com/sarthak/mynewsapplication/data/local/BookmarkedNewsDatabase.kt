package com.sarthak.mynewsapplication.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [BookmarkedNewsItem::class], version = 1, exportSchema = false)
abstract class BookmarkedNewsDatabase: RoomDatabase() {
    abstract fun bookmarkedNewsDao(): BookmarkedNewsDao
}