package com.example.testapp.ui.userdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.testapp.R
import com.example.testapp.databinding.UserDetailFragmentBinding
import com.example.testapp.data.entities.user.UserResponseItem
import com.example.testapp.utils.Resource
import com.example.testapp.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class UserDetailFragment : Fragment() {

    private var binding: UserDetailFragmentBinding by autoCleared()
    private val viewModel: UserDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = UserDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getInt("id")?.let { viewModel.start(it) }
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.users.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    it.data?.let { it1 -> bindCharacter(it1) }
                    binding.progressBar.visibility = View.GONE
                    binding.characterCl.visibility = View.VISIBLE
                }

                Resource.Status.ERROR ->
                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()

                Resource.Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.characterCl.visibility = View.GONE
                }
            }
        })
    }

    private fun bindCharacter(user: UserResponseItem) {
        binding.name.text = user.name
        binding.street.text = user.address.street
        binding.suite.text = user.address.suite
        binding.city.text = user.address.city
        binding.zipcode.text = user.address.zipcode
        binding.geo.text = user.address.geo.toString()
        binding.email.text = user.email
        binding.phone.text = user.phone
        binding.username.text = user.username
        binding.website.text = user.website
        binding.company.text = user.company.toString()
        binding.userId.text = user.id.toString()
        Glide.with(binding.root)
            .load(R.drawable.icon)
            .transform(CircleCrop())
            .into(binding.image)
    }
}
