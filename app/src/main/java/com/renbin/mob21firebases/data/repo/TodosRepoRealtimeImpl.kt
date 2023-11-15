package com.renbin.mob21firebases.data.repo

//import com.google.firebase.database.DataSnapshot
//import com.google.firebase.database.DatabaseError
//import com.google.firebase.database.DatabaseReference
//import com.google.firebase.database.ValueEventListener
//import com.google.firebase.database.getValue
//import com.renbin.mob21firebases.data.model.Todo
//import kotlinx.coroutines.channels.awaitClose
//import kotlinx.coroutines.flow.callbackFlow
//import kotlinx.coroutines.tasks.await
//
//class TodosRepoRealtimeImpl(
//    private val dbRef: DatabaseReference
//): TodosRepo {
//    override fun getAllTodos() = callbackFlow{
//        val listener = object :ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val todos = mutableListOf<Todo>()
//                for(todoSnapshot in snapshot.children){
//                    todoSnapshot.getValue<Todo>()?.let {
//                        todos.add(it.copy(id= todoSnapshot.key ?: ""))
//                    }
//                }
//                trySend(todos)
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                throw error.toException()
//            }
//
//        }
//        dbRef.addValueEventListener(listener)
//        awaitClose()
//    }
//
//    override suspend fun insert(todo: Todo) {
//        dbRef.push().setValue(todo.toHashMap()).await()
//    }
//
//    override suspend fun getTodo(id: String): Todo? {
//        val item = dbRef.child(id).get().await()
//        return item.key?.let {
//            item.getValue<Todo>()?.copy(id = it)
//        }
//    }
//
//    override suspend fun update(id: String, todo: Todo) {
//        if(id.isEmpty()) return
//        dbRef.child(id).setValue(todo.toHashMap())
//    }
//
//    override suspend fun delete(id: String) {
//        dbRef.child(id).removeValue()
//    }
//}