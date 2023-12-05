package com.niharikainala.yummytummy.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.niharikainala.yummytummy.pojo.Meal
import com.niharikainala.yummytummy.pojo.MealDB
import com.niharikainala.yummytummy.pojo.MealDetail
import com.niharikainala.yummytummy.pojo.MealList
import com.niharikainala.yummytummy.pojo.MealsByCategoryList
import com.niharikainala.yummytummy.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel : ViewModel() {
    private var mealDetailLiveData = MutableLiveData<MealList>()

    fun getMealDetailById(id:String){
        RetrofitInstance.api.getMealById(id).enqueue(object : Callback<MealList>{
            override fun onResponse(
                call: Call<MealList>,
                response: Response<MealList>
            ) {
                if(response.body()!=null){
                    mealDetailLiveData.value = response.body()
                }
                else{
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("MealActivity",t.message.toString())
            }

        })
    }

    fun observeMealDetailLiveData():LiveData<MealList>{
        return mealDetailLiveData
    }
}