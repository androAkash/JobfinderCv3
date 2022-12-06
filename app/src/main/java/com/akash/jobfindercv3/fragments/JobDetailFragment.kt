package com.akash.jobfindercv3.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.akash.jobfindercv3.MainActivity
import com.akash.jobfindercv3.databinding.FragmentJobDetailBinding
import com.akash.jobfindercv3.models.FavouriteJob
import com.akash.jobfindercv3.models.Job
import com.akash.jobfindercv3.viewModel.RemoteJobViewModel
import com.google.android.material.snackbar.Snackbar


class JobDetailFragment : Fragment() {

    private lateinit var binding: FragmentJobDetailBinding
    private lateinit var currentJob: Job
    private lateinit var viewModel: RemoteJobViewModel

    private val args : JobDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentJobDetailBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        currentJob = args.job!!

        setUpWebView()

        binding.fabAddFavorite.setOnClickListener {
            addFavJob(view)
        }
    }

    private fun addFavJob(view: View) {
        val favJob = FavouriteJob(
            0,
            currentJob.candidateRequiredLocation, currentJob.category,
            currentJob.companyLogoUrl, currentJob.companyName,
            currentJob.description, currentJob.id, currentJob.jobType,
            currentJob.publicationDate, currentJob.salary, currentJob.title, currentJob.url
        )
        viewModel.addFavJob(favJob)
        Snackbar.make(view,"Job saved Successfully",Snackbar.LENGTH_LONG).show()
    }

    private fun setUpWebView() {
        binding.webView.apply {
            webViewClient = WebViewClient()
            currentJob.url?.let { loadUrl(it) }
        }
        val settings = binding.webView.settings
        settings.javaScriptEnabled = true
        settings.cacheMode= WebSettings.LOAD_NO_CACHE
        settings.setSupportZoom(false)
        settings.builtInZoomControls = false
        settings.displayZoomControls = false
        settings.textZoom = 100
        settings.blockNetworkImage = false
        settings.loadsImagesAutomatically = true
    }

}