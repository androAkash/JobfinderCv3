package com.akash.jobfindercv3.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.akash.jobfindercv3.models.FavouriteJob

@Dao
interface FavouriteJobDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavouriteJob(job:FavouriteJob)

    @Query("SELECT * FROM fav_job ORDER BY id DESC")
    fun getAllFavouriteJob():LiveData<List<FavouriteJob>>

    @Delete
    suspend fun deleteFavJob(job: FavouriteJob)
}