package com.akash.jobfindercv3.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.akash.jobfindercv3.MainActivity
import com.akash.jobfindercv3.R
import com.akash.jobfindercv3.adapters.RemoteJobAdapter
import com.akash.jobfindercv3.databinding.FragmentRemoteJobBinding
import com.akash.jobfindercv3.utils.Constants
import com.akash.jobfindercv3.viewModel.RemoteJobViewModel


class RemoteJobFragment : Fragment(R.layout.fragment_remote_job) ,SwipeRefreshLayout.OnRefreshListener{
    private var _binding: FragmentRemoteJobBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: RemoteJobViewModel
    private lateinit var remoteJobAdapter: RemoteJobAdapter
    private lateinit var swipeLayout: SwipeRefreshLayout



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRemoteJobBinding.inflate(
            inflater, container, false
        )

        swipeLayout = binding.swipeContainer
        swipeLayout.setOnRefreshListener(this)
        swipeLayout.setColorSchemeColors(
            Color.GREEN, Color.RED,
            Color.BLUE, Color.CYAN
        )

        swipeLayout.post{
            swipeLayout.isRefreshing = true
            setUpRecyclerView()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        setUpRecyclerView()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onRefresh() {
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        remoteJobAdapter = RemoteJobAdapter()

        binding.rvRemoteJobs.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            addItemDecoration(object :
                DividerItemDecoration(activity, LinearLayoutManager.VERTICAL) {})

            adapter = remoteJobAdapter
        }
        fetchingData()
    }

    private fun fetchingData() {

        if (Constants.isNetworkAvailable(requireContext())) {
            viewModel.remoteJobResult().observe(viewLifecycleOwner) { remoteJob ->
                if (remoteJob != null) {
                    remoteJobAdapter.differ.submitList(remoteJob.jobs)
                    swipeLayout.isRefreshing = false
                }
            }
        } else{
            Toast.makeText(activity,"No internet connection",Toast.LENGTH_LONG).show()
            swipeLayout.isRefreshing = false
        }
    }


}