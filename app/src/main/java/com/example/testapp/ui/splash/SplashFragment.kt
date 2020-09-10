package com.example.testapp.ui.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.testapp.R
import com.example.testapp.databinding.FragmentSplashBinding
import com.example.testapp.utils.Resource
import com.example.testapp.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class SplashFragment : Fragment() {

    private var binding: FragmentSplashBinding by autoCleared()
    private val viewModel: SplashViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.response.observe(viewLifecycleOwner, Observer {
            Timber.d(it.toString())
            if(it.first.status == Resource.Status.SUCCESS && it.second.status == Resource.Status.SUCCESS){
                binding.progressBar.visibility = View.GONE
                findNavController().navigate(
                    R.id.action_splashFragment_to_todoFragment
                )
            }else if(it.first.status == Resource.Status.ERROR || it.second.status == Resource.Status.ERROR){
                Timber.e(it.first.message)
                Toast.makeText(requireContext(), it.first.message, Toast.LENGTH_SHORT).show()
            }else{
                binding.progressBar.visibility = View.VISIBLE
            }
        })

    }
}