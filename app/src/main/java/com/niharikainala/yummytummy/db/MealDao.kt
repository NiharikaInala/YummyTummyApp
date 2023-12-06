package com.niharikainala.yummytummy.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.niharikainala.yummytummy.pojo.MealDetail

@Dao
interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertMeal(mealDetail:MealDetail)

//    @Update
//    suspend fun updateMeal(mealDetail: MealDetail)

    @Delete
    suspend fun delete(mealDetail: MealDetail)

    @Query("SElECT * FROM meal_information")
    fun getAllMeals():LiveData<List<MealDetail>>

}