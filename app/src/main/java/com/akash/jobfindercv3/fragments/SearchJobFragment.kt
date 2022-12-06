package com.akash.jobfindercv3.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.akash.jobfindercv3.MainActivity
import com.akash.jobfindercv3.R
import com.akash.jobfindercv3.adapters.FavJobAdapter
import com.akash.jobfindercv3.adapters.RemoteJobAdapter
import com.akash.jobfindercv3.databinding.FragmentSearchJobBinding
import com.akash.jobfindercv3.models.Job
import com.akash.jobfindercv3.utils.Constants
import com.akash.jobfindercv3.viewModel.RemoteJobViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchJobFragment : Fragment() {

    private lateinit var binding: FragmentSearchJobBinding
    private lateinit var viewModel: RemoteJobViewModel
    private lateinit var jobAdapter: RemoteJobAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchJobBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        if (Constants.isNetworkAvailable(requireContext())){
        searchJob()
        setUpRecyclerView()
        } else{
            Toast.makeText(activity,"No Internet Connection",Toast.LENGTH_LONG).show()
        }

    }

    private fun searchJob() {
        var job: kotlinx.coroutines.Job? = null
        binding.etSearch.addTextChangedListener{text->
            job?.cancel()
            job = MainScope().launch {
                delay(500L)
                text?.let{
                    if (text.toString().isNotEmpty()){
                        viewModel.searchRemoteJob(text.toString())
                    }
                }
            }
        }
    }

    private fun setUpRecyclerView(){
        jobAdapter = RemoteJobAdapter()
        binding.rvSearchJobs.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = jobAdapter
        }
        viewModel.searchResult().observe(viewLifecycleOwner) { remoteJob ->
            jobAdapter.differ.submitList(remoteJob?.jobs)
        }
    }
}