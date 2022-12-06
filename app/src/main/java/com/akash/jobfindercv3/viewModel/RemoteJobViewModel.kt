package com.akash.jobfindercv3.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.akash.jobfindercv3.models.FavouriteJob
import com.akash.jobfindercv3.repository.RemoteJobRepository
import kotlinx.coroutines.launch

class RemoteJobViewModel(
    app: Application,
    private val remoteJobRepository: RemoteJobRepository
) : AndroidViewModel(app) {


    fun remoteJobResult() = remoteJobRepository.remoteJobResult()

    fun addFavJob(job: FavouriteJob) = viewModelScope.launch {
        remoteJobRepository.addFavouriteJob(job)
    }

    fun deleteJob(job: FavouriteJob) = viewModelScope.launch {
        remoteJobRepository.deleteJob(job)
    }

    fun getAllFavJobs() = remoteJobRepository.getAllFavJobs()

    fun searchRemoteJob(query: String?) = remoteJobRepository.searchJobResponse(query)

    fun searchResult() = remoteJobRepository.searchJobResult()
}