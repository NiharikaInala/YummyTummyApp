package com.niharikainala.yummytummy.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.niharikainala.yummytummy.db.MealDatabase
import com.niharikainala.yummytummy.pojo.Category
import com.niharikainala.yummytummy.pojo.CategoryList
import com.niharikainala.yummytummy.pojo.MealsByCategoryList
import com.niharikainala.yummytummy.pojo.MealDetail
import com.niharikainala.yummytummy.pojo.MealList
import com.niharikainala.yummytummy.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val mealDatabase: MealDatabase) : ViewModel() {
    private var randomMealLiveData = MutableLiveData<MealList>()
    private var popularMealLiveData = MutableLiveData<MealsByCategoryList>()
    private var categoryMealLiveData = MutableLiveData<List<Category>>()
    private var favMealsLiveData = mealDatabase.mealDao().getAllMeals()
    private var bottomSheetLiveData = MutableLiveData<MealDetail>()
    private var searchMealLiveData = MutableLiveData<List<MealDetail>>()

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

    fun searchMeals(searchQuery:String) = RetrofitInstance.api.searchItems(searchQuery).enqueue(object: Callback<MealList>{
        override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
            val mealsList = response.body()?.meals
            mealsList.let {
                searchMealLiveData.postValue(it)
            }
        }

        override fun onFailure(call: Call<MealList>, t: Throwable) {
            Log.e("HomeViewModel",t.message.toString())
        }

    })

    fun getMealById(id:String){
        RetrofitInstance.api.getMealById(id).enqueue(object : Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                val meal = response.body()?.meals?.first()
                meal.let {
                    meal -> bottomSheetLiveData.postValue(meal)
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.e("HomeViewModel",t.message.toString())
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
                    categoryMealLiveData.value = response.body()!!.categories
                } else {
                    return
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.d("HomeFragment", t.message.toString())
            }
        })
    }

    fun deleteMeal(mealDetail: MealDetail){
        viewModelScope.launch {
            mealDatabase.mealDao().delete(mealDetail)
        }
    }

    fun insertMeal(mealDetail: MealDetail){
        viewModelScope.launch {
            mealDatabase.mealDao().upsertMeal(mealDetail)
        }
    }

    fun observeCategoryMealLiveData(): LiveData<List<Category>> {
        return categoryMealLiveData
    }

    fun observePopularMealLiveData(): LiveData<MealsByCategoryList> {
        return popularMealLiveData
    }

    fun observeRandomMealLivedata(): LiveData<MealList> {
        return randomMealLiveData
    }

    fun observeFavMealsLiveData():LiveData<List<MealDetail>>{
      return favMealsLiveData
    }

    fun observeBottomMealLiveData():LiveData<MealDetail>{
        return bottomSheetLiveData
    }

    fun observeSearchMealLiveData():LiveData<List<MealDetail>>{
        return searchMealLiveData
    }
}