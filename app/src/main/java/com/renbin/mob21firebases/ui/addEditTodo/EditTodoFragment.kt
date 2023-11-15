package com.renbin.mob21firebases.ui.addEditTodo

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.renbin.mob21firebases.R
import com.renbin.mob21firebases.ui.addEditTodo.viewModel.EditTodoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditTodoFragment: BaseAddEditTodoFragment() {
    override val viewModel: EditTodoViewModel by viewModels()
    val args: EditTodoFragmentArgs by navArgs()

    override fun setupUIComponents() {
        super.setupUIComponents()
        binding.btnSubmit.text = getText(R.string.update)
        viewModel.getTodo(args.todoId)
    }

    override fun setupViewModelObserver() {
        super.setupViewModelObserver()
        lifecycleScope.launch {
            viewModel.todo.collect{
                binding.etTitle.setText(it.title)
                binding.etDesc.setText(it.desc)
            }
        }
    }
}