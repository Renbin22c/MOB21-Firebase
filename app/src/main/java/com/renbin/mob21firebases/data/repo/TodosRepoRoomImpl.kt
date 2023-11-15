package com.renbin.mob21firebases.data.repo
//
//import com.renbin.mob21firebases.data.db.TodoDao
//import com.renbin.mob21firebases.data.model.Todo
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.map
//
//class TodosRepoRoomImpl(
//    private val dao: TodoDao
//): TodosRepo {
//    override fun getAllTodos(): Flow<List<Todo>> {
//        return dao.getAllTodos().map {
//            it.map { todo -> todo.copy(id = todo._id.toString()) }
//        }
//    }
//
//    override suspend fun insert(todo: Todo) {
//        dao.insert(todo)
//    }
//
//    override suspend fun getTodo(id: String): Todo? {
//        val todo =  dao.getById(id.toInt())
//        return todo?.copy(id = todo._id.toString())
//    }
//
//    override suspend fun update(id: String, todo: Todo) {
//        dao.insert(todo.copy(_id = id.toInt()))
//    }
//
//    override suspend fun delete(id: String) {
//        dao.delete(id.toInt())
//    }
//
//}