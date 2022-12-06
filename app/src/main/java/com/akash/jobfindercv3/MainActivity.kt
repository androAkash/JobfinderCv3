package com.akash.jobfindercv3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.akash.jobfindercv3.databinding.ActivityMainBinding
import com.akash.jobfindercv3.db.FavouriteJobDatabase
import com.akash.jobfindercv3.repository.RemoteJobRepository
import com.akash.jobfindercv3.viewModel.RemoteJobViewModel
import com.akash.jobfindercv3.viewModel.RemoteJobViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: RemoteJobViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""

        setUpViewModel()
    }

    private fun setUpViewModel() {
        val remoteJobRepository = RemoteJobRepository(
            FavouriteJobDatabase(this)
        )

        val viewModelProviderFactory = RemoteJobViewModelFactory(
            application,
            remoteJobRepository
        )

        viewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(RemoteJobViewModel::class.java)
    }
}