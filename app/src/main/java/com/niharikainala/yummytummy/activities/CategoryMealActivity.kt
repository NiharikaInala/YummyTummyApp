package com.niharikainala.yummytummy.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.niharikainala.yummytummy.R
import com.niharikainala.yummytummy.adapters.CategoryMealAdapter
import com.niharikainala.yummytummy.databinding.ActivityCategoryMealBinding
import com.niharikainala.yummytummy.fragments.HomeFragment
import com.niharikainala.yummytummy.viewmodel.CategoryMealsViewModel

class CategoryMealActivity : AppCompatActivity() {
    lateinit var binding : ActivityCategoryMealBinding
    lateinit var mvvm : CategoryMealsViewModel
    lateinit var categoryMealAdapter: CategoryMealAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prepareRecyclerView()
        mvvm = ViewModelProviders.of(this)[CategoryMealsViewModel::class.java]

        mvvm.getMealsByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)
        mvvm.observeMealsLiveData().observe(this, Observer { mealsList ->
            binding.tvCategoryCount.text = mealsList.size.toString()
            categoryMealAdapter.setMealList(mealsList)
        })
    }

    private fun prepareRecyclerView(){
        categoryMealAdapter = CategoryMealAdapter()
        binding.mealRecyclerview.apply {
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter = categoryMealAdapter
        }
    }
}