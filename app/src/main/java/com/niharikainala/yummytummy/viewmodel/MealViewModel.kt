package com.niharikainala.yummytummy.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.niharikainala.yummytummy.data.db.MealDatabase
import com.niharikainala.yummytummy.data.pojo.MealDetail
import com.niharikainala.yummytummy.data.pojo.MealList
import com.niharikainala.yummytummy.data.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel(val mealDatabase: MealDatabase) : ViewModel() {

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

    fun insertMeal(mealDetail: MealDetail){
        viewModelScope.launch {
            mealDatabase.mealDao().upsertMeal(mealDetail)
        }
    }


}