package com.akash.jobfindercv3.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "fav_job")
data class FavouriteJob(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val candidateRequiredLocation: String?,
    val category: String?,
    val companyLogoUrl: String?,
    val companyName: String?,
    val description: String?,
    val jobId: Int?,
    val jobType: String?,
    val publicationDate: String?,
    val salary: String?,
    val title: String?,
    val url: String?
)