package com.renbin.mob21firebases.ui.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.renbin.mob21firebases.R
import kotlinx.coroutines.launch

abstract class BaseFragment<T : ViewBinding> : Fragment() {
    protected lateinit var navController: NavController
    protected lateinit var binding: T
    protected abstract val viewModel: BaseViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = NavHostFragment.findNavController(this)
        onFragmentResult()
        setupUIComponents()
        setupViewModelObserver()
    }

    protected open fun onFragmentResult() {}

    protected open fun setupViewModelObserver() {
        lifecycleScope.launch {
            viewModel.error.collect {
                showSnackbar(it, true)
            }
        }

        lifecycleScope.launch {
            viewModel.success.collect {
                showSnackbar(it)
            }
        }
    }

    protected open fun setupUIComponents() {}

    fun showSnackbar(msg: String, isError: Boolean = false) {
        val snackbar = Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG)
        if (isError) {
            snackbar.setBackgroundTint(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.error
                )
            )
        }
        snackbar.show()
    }
}