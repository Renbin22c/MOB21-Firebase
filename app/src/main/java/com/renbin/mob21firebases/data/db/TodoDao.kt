package com.renbin.mob21firebases.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.renbin.mob21firebases.data.model.Todo
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Query("select * from todo")
    fun getAllTodos(): Flow<List<Todo>>

    @Query("select * from todo where todoId = :id")
    fun getById(id: Int): Todo?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(todo: Todo)

    @Query("delete from todo where todoId = :id")
    fun delete(id: Int)
}