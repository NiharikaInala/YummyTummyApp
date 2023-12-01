package com.niharikainala.yummytummy.retrofit

import com.niharikainala.yummytummy.pojo.CategoryList
import com.niharikainala.yummytummy.pojo.MealsByCategoryList
import com.niharikainala.yummytummy.pojo.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {
    @GET("random.php")
    fun getRandomMeal(): Call<MealList>

    @GET("Lookup.php")
    fun getMealById(@Query("i")id:String): Call<MealList>

    @GET("filter.php")
    fun getPopularItems(@Query("c")categoryName:String):Call<MealsByCategoryList>

    @GET("categories.php")
    fun getCategories(): Call<CategoryList>
}