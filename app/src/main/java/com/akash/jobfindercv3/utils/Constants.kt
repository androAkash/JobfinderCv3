package com.akash.jobfindercv3.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

object Constants {
    const val BASE_URL = "https://remotive.com/api/"

    fun isNetworkAvailable(context: Context): Boolean {
        // It answers the queries about the state of network connectivity.
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetWork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            activeNetWork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetWork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            //for other device how are able to connect with Ethernet
            activeNetWork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}