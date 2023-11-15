package com.renbin.mob21firebases.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.renbin.mob21firebases.data.model.Todo

@Database(entities = [Todo::class], version = 1)
abstract class TodoDatabase: RoomDatabase() {
    abstract fun todoDao(): TodoDao

    companion object {
        const val DB_NAME = "todo_database"
    }
}