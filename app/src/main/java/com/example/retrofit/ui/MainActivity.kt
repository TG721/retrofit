package com.example.retrofit.ui

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.*
import com.example.retrofit.MainViewModel
import com.example.retrofit.MainViewModelFactory
import com.example.retrofit.MyDataItem
import com.example.retrofit.R
import com.example.retrofit.databinding.ActivityMainBinding
import com.example.retrofit.repository.Repository
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        viewModel.getInfo()

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.myState.collect{
                    when(it){
                        is MainViewModel.MyResponseState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is MainViewModel.MyResponseState.Error -> {
                            binding.finalText.text = it.message
                            binding.progressBar.visibility = View.GONE
                        }
                        is MainViewModel.MyResponseState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            binding.finalText.text = it.items.toString()
                        }
                        else -> {}
                    }
                }
            }
        }
    }
}