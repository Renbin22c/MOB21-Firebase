package com.renbin.mob21firebases.data.repo

import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.renbin.mob21firebases.core.TAG
import com.renbin.mob21firebases.data.model.Todo
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class TodosRepoFireStoreImpl (
    private val db: CollectionReference
): TodosRepo {
    override fun getAllTodos()= callbackFlow {
        val listener = db.addSnapshotListener { value, error ->
            if(error != null) {
                Log.d(TAG, error.message.toString())
                throw error
            }
            val todos = mutableListOf<Todo>()
            value?.documents?.let { items ->
                for (item in items) {
                    item.data?.let {
                        it["id"] = item.id
                        todos.add(Todo.fromHashMap(it))
                    }
                }
                trySend(todos)
            }
        }
        awaitClose {
            listener.remove()
        }
    }

    override suspend fun insert(todo: Todo) {
        db.add(todo.toHashMap())
            .addOnSuccessListener {
                Log.d(TAG, "Added successfully")
            }.addOnFailureListener {
                Log.d(TAG, it.message.toString())
                throw it
            }
    }

    override suspend fun getTodo(id: String): Todo? {
        val snapshot = db.document(id).get().await()
        return snapshot.data?.let {
            it["id"] = snapshot.id
            Todo.fromHashMap(it)
        }
    }

    override suspend fun update(id: String, todo: Todo) {
        db.document(id).set(todo.toHashMap()).await()
    }

    override suspend fun delete(id: String) {
        db.document(id).delete().await()
    }

    override suspend fun greet(): String {
        val publicDb = FirebaseFirestore.getInstance().collection("public")
        val snapshot = publicDb.document("greeting").get().await()
        return snapshot.data?.let {
            it["msg"].toString()
        } ?: "Hello User"
    }
}