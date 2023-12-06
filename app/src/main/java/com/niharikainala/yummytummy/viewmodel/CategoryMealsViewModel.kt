package com.niharikainala.yummytummy.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.niharikainala.yummytummy.pojo.Meal
import com.niharikainala.yummytummy.pojo.MealsByCategoryList
import com.niharikainala.yummytummy.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class CategoryMealsViewModel : ViewModel(){

     val mealsLiveData = MutableLiveData<List<Meal>>()
    fun getMealsByCategory(categoryName:String){
        RetrofitInstance.api.getMealsByCategory(categoryName).enqueue(object : retrofit2.Callback<MealsByCategoryList>{
            override fun onResponse(
                call: Call<MealsByCategoryList>,
                response: Response<MealsByCategoryList>
            ) {
                if(response.body()!=null){
                    mealsLiveData.postValue(response.body()!!.meals)
                }
            }

            override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                Log.e("CategoryViewModel",t.message.toString())
            }

        })
    }

    fun observeMealsLiveData(): LiveData<List<Meal>>{
        return mealsLiveData
    }
}