package com.renbin.mob21firebases.ui.addEditTodo

import androidx.fragment.app.viewModels
import com.renbin.mob21firebases.ui.addEditTodo.viewModel.AddTodoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTodoFragment: BaseAddEditTodoFragment() {
    override val viewModel: AddTodoViewModel by viewModels()
}