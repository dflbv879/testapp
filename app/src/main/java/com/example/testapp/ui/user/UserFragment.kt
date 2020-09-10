package com.example.testapp.ui.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.testapp.R
import com.example.testapp.data.entities.user.UserResponseItem
import com.example.testapp.databinding.FragmentUserBinding
import com.example.testapp.utils.Resource
import com.example.testapp.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFragment : Fragment() {

    private val viewModel: UserViewModel by viewModels()
    private var binding: FragmentUserBinding by autoCleared()
    private var userId : Int? = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userId = arguments?.getInt("id")
        userId?.let { viewModel.start(it) }
        binding.userMainSection.setOnClickListener {
            findNavController().navigate(
            R.id.action_userFragment_to_userDetailFragment,
            bundleOf("id" to userId)
        ) }
        setupObservers()
    }


    private fun setupObservers() {
        viewModel.user.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    it.data?.let { it1 -> bindCharacter(it1) }
                    binding.progressBar.visibility = View.GONE
                    binding.userMainSection.visibility = View.VISIBLE
                }

                Resource.Status.ERROR ->
                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()

                Resource.Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.userMainSection.visibility = View.GONE
                }
            }
        })
    }

    private fun bindCharacter(user: UserResponseItem) {
        binding.name.text = user.name

        Glide.with(binding.root)
            .load(R.drawable.icon)
            .transform(CircleCrop())
            .into(binding.image)
    }
}
