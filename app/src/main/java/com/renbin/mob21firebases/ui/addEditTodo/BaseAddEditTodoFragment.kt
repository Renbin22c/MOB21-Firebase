package com.renbin.mob21firebases.ui.addEditTodo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.renbin.mob21firebases.databinding.FragmentBaseAddEditTodoBinding
import com.renbin.mob21firebases.ui.addEditTodo.viewModel.BaseAddEditTodoViewModel
import com.renbin.mob21firebases.ui.base.BaseFragment
import kotlinx.coroutines.launch

abstract class BaseAddEditTodoFragment : BaseFragment<FragmentBaseAddEditTodoBinding>() {
    abstract override val viewModel: BaseAddEditTodoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBaseAddEditTodoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUIComponents() {
        super.setupUIComponents()

        binding.btnSubmit.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val desc = binding.etDesc.text.toString()
            viewModel.submit(title, desc)
        }
    }

    override fun setupViewModelObserver() {
        super.setupViewModelObserver()
        lifecycleScope.launch {
            viewModel.finish.collect{
                navController.popBackStack()
            }
        }
    }

}