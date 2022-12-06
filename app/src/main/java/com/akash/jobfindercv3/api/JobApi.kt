package com.akash.jobfindercv3.api

import android.widget.RemoteViews.RemoteResponse
import com.akash.jobfindercv3.models.RemoteJobResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface JobApi {
    @GET("remote-jobs?limit=25")
    fun getRemoteJobResponse():Call<RemoteJobResponse>

    @GET("remote-jobs")
    fun searchJob(@Query("search")query:String?):Call<RemoteJobResponse>
}