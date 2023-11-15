package com.renbin.mob21firebases.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.renbin.mob21firebases.R
import com.renbin.mob21firebases.databinding.FragmentProfileBinding
import com.renbin.mob21firebases.ui.base.BaseFragment
import com.renbin.mob21firebases.ui.tabContainer.TabContainerFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {
    override val viewModel: ProfileViewModel by viewModels()
    private lateinit var pickMedia: ActivityResultLauncher<PickVisualMediaRequest>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pickMedia = registerForActivityResult(
                ActivityResultContracts.PickVisualMedia()) { uri ->
                if (uri != null) {
                    viewModel.updateProfilePic(uri)
                } else {
                    Log.d("PhotoPicker", "No media selected")
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUIComponents() {
        super.setupUIComponents()

        binding.ibLogout.setOnClickListener {
            viewModel.logout()
        }

        binding.icEditProfile.setOnClickListener {
            pickMedia.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }
    }

    override fun setupViewModelObserver() {
        super.setupViewModelObserver()

        lifecycleScope.launch {
            viewModel.finish.collect{
                val action = TabContainerFragmentDirections.actionGlobalLoginFragment()
                navController.navigate(action)
            }
        }

        lifecycleScope.launch {
            viewModel.user.collect{
                binding.tvName.text = it.name
                binding.tvEmail.text = it.email
            }
        }

        lifecycleScope.launch {
            viewModel.profileUri.collect{
                Glide.with(binding.root)
                    .load(it)
                    .placeholder(R.drawable.ic_person)
                    .into(binding.ivImage)
            }
        }
    }


}