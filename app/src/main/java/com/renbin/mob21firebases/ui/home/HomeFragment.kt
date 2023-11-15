package com.renbin.mob21firebases.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.renbin.mob21firebases.R
import com.renbin.mob21firebases.core.utils.NativeUtils
import com.renbin.mob21firebases.data.model.Todo
import com.renbin.mob21firebases.databinding.FragmentHomeBinding
import com.renbin.mob21firebases.ui.adapater.TodosAdapter
import com.renbin.mob21firebases.ui.base.BaseFragment
import com.renbin.mob21firebases.ui.tabContainer.TabContainerFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter: TodosAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun setupUIComponents() {
        super.setupUIComponents()
        setupAdapter()

        binding.btnAdd.setOnClickListener {
            val action = TabContainerFragmentDirections.actionHomeToAddTodo()
            navController.navigate(action)
        }

        binding.tvText.text = NativeUtils.greet()
    }


    override fun setupViewModelObserver() {
        super.setupViewModelObserver()

        lifecycleScope.launch {
            viewModel.todos.collect {
                adapter.setTodos(it)
            }
        }
    }

    override fun onFragmentResult() {
        super.onFragmentResult()

        setFragmentResultListener("from_add_or_update") { _, result ->
            val refresh = result.getBoolean("refresh")
            if (refresh) {
                viewModel.refresh()
            }
        }
    }

    private fun setupAdapter() {
        adapter = TodosAdapter(emptyList())
        adapter.listener = object: TodosAdapter.Listener{
            override fun onClick(todo: Todo) {
                Log.d("debugging", todo.toString())
//                val action = HomeFragmentDirections.actionHomeToEditTodo(todo.id)
//                navController.navigate(action)
            }

            override fun onDelete(todo: Todo) {
                viewModel.delete(todo)
            }

        }
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvTodos.adapter = adapter
        binding.rvTodos.layoutManager = layoutManager
    }
}