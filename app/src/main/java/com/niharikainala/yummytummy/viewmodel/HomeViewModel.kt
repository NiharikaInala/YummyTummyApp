package com.niharikainala.yummytummy.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.niharikainala.yummytummy.pojo.Category
import com.niharikainala.yummytummy.pojo.CategoryList
import com.niharikainala.yummytummy.pojo.MealsByCategoryList
import com.niharikainala.yummytummy.pojo.Meal
import com.niharikainala.yummytummy.pojo.MealList
import com.niharikainala.yummytummy.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel() : ViewModel() {
    private var randomMealLiveData = MutableLiveData<MealList>()
    private var popularMealLiveData = MutableLiveData<MealsByCategoryList>()
    private var categoritMealLiveData = MutableLiveData<List<Category>>()

    fun getRandomMeal() {
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null) {
                    randomMealLiveData.value = response.body()
                } else {
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("HomeFragment", t.message.toString())
            }
        })
    }

    fun getPopularItems(categoryName: String) {
        RetrofitInstance.api.getPopularItems(categoryName)
            .enqueue(object : Callback<MealsByCategoryList> {
                override fun onResponse(
                    call: Call<MealsByCategoryList>,
                    response: Response<MealsByCategoryList>
                ) {
                    if (response.body() != null) {
                        popularMealLiveData.value = response.body()

                    } else {
                        return
                    }
                }

                override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                    Log.d("HomeFragment", t.message.toString())
                }
            })
    }

    fun getCategoryMeals() {
        RetrofitInstance.api.getCategories().enqueue(object : Callback<CategoryList> {
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                if (response.body() != null) {
                    categoritMealLiveData.value = response.body()!!.categories
                } else {
                    return
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.d("HomeFragment", t.message.toString())
            }
        })
    }

    fun observeCategoryMealLiveData(): LiveData<List<Category>> {
        return categoritMealLiveData
    }

    fun observePopularMealLiveData(): LiveData<MealsByCategoryList> {
        return popularMealLiveData
    }

    fun observeRandomMealLivedata(): LiveData<MealList> {
        return randomMealLiveData
    }
}