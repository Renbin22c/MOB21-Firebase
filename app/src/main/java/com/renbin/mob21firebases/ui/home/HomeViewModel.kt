package com.renbin.mob21firebases.ui.home

import androidx.lifecycle.viewModelScope
import com.renbin.mob21firebases.data.model.Todo
import com.renbin.mob21firebases.data.repo.TodosRepo
import com.renbin.mob21firebases.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: TodosRepo
) : BaseViewModel() {

    private val _todos: MutableStateFlow<List<Todo>> = MutableStateFlow(emptyList())
    val todos: StateFlow<List<Todo>> = _todos

    init {
        getAllTodos()
    }

    fun getAllTodos() {
        viewModelScope.launch(Dispatchers.IO) {
            safeApiCall {
                repo.getAllTodos()
            }?.collect{
                _todos.value = it
            }
        }
    }

    fun delete(todo: Todo){
        viewModelScope.launch(Dispatchers.IO) {
            repo.delete(todo.id)
        }
    }

    fun refresh() {
        getAllTodos()
    }

}