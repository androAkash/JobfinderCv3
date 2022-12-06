package com.akash.jobfindercv3.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.akash.jobfindercv3.api.RetrofitInstance
import com.akash.jobfindercv3.db.FavouriteJobDatabase
import com.akash.jobfindercv3.models.FavouriteJob
import com.akash.jobfindercv3.models.Job
import com.akash.jobfindercv3.models.RemoteJobResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteJobRepository(private val db: FavouriteJobDatabase) {
    private val remoteJobService = RetrofitInstance.api
    private val remoteJobResponseLiveData:MutableLiveData<RemoteJobResponse?> = MutableLiveData()
    private val searchJobResponseLiveData:MutableLiveData<RemoteJobResponse?> = MutableLiveData()

    init {
        getRemoteJobResponse()
    }

    private fun getRemoteJobResponse(){
        remoteJobService.getRemoteJobResponse().enqueue(
            object : Callback<RemoteJobResponse>{
                override fun onResponse(
                    call: Call<RemoteJobResponse>,
                    response: Response<RemoteJobResponse>
                ) {
                    remoteJobResponseLiveData.postValue(response.body())
                }

                override fun onFailure(call: Call<RemoteJobResponse>, t: Throwable) {
                    remoteJobResponseLiveData.postValue(null)
                    Log.d("TAG", "onFailure: ${t.message}")
                }
            }
        )
    }

    fun searchJobResponse(query:String?){
        remoteJobService.searchJob(query).enqueue(
            object : Callback<RemoteJobResponse>{
                override fun onResponse(
                    call: Call<RemoteJobResponse>,
                    response: Response<RemoteJobResponse>
                ) {
                    searchJobResponseLiveData.postValue(response.body())
                }

                override fun onFailure(call: Call<RemoteJobResponse>, t: Throwable) {
                    searchJobResponseLiveData.postValue(null)
                }
            }
        )
    }

    fun remoteJobResult(): MutableLiveData<RemoteJobResponse?> {
        return remoteJobResponseLiveData
    }

    fun searchJobResult():MutableLiveData<RemoteJobResponse?>{
        return searchJobResponseLiveData
    }

    suspend fun addFavouriteJob(job: FavouriteJob) = db.getFavJobDao().addFavouriteJob(job)
    suspend fun deleteJob(job: FavouriteJob) = db.getFavJobDao().deleteFavJob(job)
    fun getAllFavJobs() = db.getFavJobDao().getAllFavouriteJob()
}