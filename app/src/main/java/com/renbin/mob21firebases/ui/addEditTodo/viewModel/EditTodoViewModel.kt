package com.renbin.mob21firebases.ui.addEditTodo.viewModel

import androidx.lifecycle.viewModelScope
import com.renbin.mob21firebases.data.model.Todo
import com.renbin.mob21firebases.data.repo.TodosRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditTodoViewModel @Inject constructor(
    private val repo: TodosRepo
): BaseAddEditTodoViewModel() {
    private val _todo: MutableStateFlow<Todo> = MutableStateFlow(
        Todo(title = "", desc = "")
    )
    val todo: StateFlow<Todo> = _todo

    fun getTodo(id:String){
        viewModelScope.launch(Dispatchers.IO){
            safeApiCall {
                repo.getTodo(id)
            }?.let {
                _todo.value = it
            }
        }
    }

    override fun submit(title: String, desc: String) {
        viewModelScope.launch(Dispatchers.IO){
            safeApiCall { repo.update(todo.value.id, todo.value.copy(title = title, desc = desc)) }
            _finish.emit(Unit)
        }
    }
}