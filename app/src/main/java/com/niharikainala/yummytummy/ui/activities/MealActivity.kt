package com.niharikainala.yummytummy.ui.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.niharikainala.yummytummy.R
import com.niharikainala.yummytummy.databinding.ActivityMealBinding
import com.niharikainala.yummytummy.data.db.MealDatabase
import com.niharikainala.yummytummy.ui.fragments.HomeFragment
import com.niharikainala.yummytummy.data.pojo.MealDetail
import com.niharikainala.yummytummy.viewmodel.MealViewModel
import com.niharikainala.yummytummy.viewmodel.MealViewModelFactory

class MealActivity : AppCompatActivity() {
    private lateinit var mealId:String
    private lateinit var mealName:String
    private lateinit var mealThumb:String
    private lateinit var binding: ActivityMealBinding
    private lateinit var youtubeLink: String
    private lateinit var mealMvvm : MealViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mealDatabase = MealDatabase.getInstance(this)
        val viewModelFactory = MealViewModelFactory(mealDatabase)
        mealMvvm = ViewModelProvider(this,viewModelFactory)[MealViewModel::class.java]
        //mealMvvm = ViewModelProviders.of(this)[MealViewModel::class.java]
        getMealInfoFromIntent()
        setInfoInViews()
        //loadingCase()
        mealMvvm.getMealDetailById(mealId)
        observeMealDetailLiveData()

        onYoutubeImageClick()
        onFavoriteClick()
    }

    private fun onFavoriteClick(){
        binding.btnSave.setOnClickListener {
            mealToSave?.let { it1 -> mealMvvm.insertMeal(it1)
            Toast.makeText(this,"Meal Saved",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onYoutubeImageClick(){
        binding.imgYoutube.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }
    private var mealToSave : MealDetail?=null
    private fun observeMealDetailLiveData(){
        mealMvvm.observeMealDetailLiveData().observe(this
        ) { value -> //onResponseCase()
            val meal = value
            mealToSave = meal.meals[0]
            binding.tvCategoryInfo.text = "Category : ${meal.meals[0].strCategory}"
            binding.tvAreaInfo.text = "Area : ${meal.meals[0].strArea}"
            binding.tvInstructions.text = meal.meals[0].strInstructions
            youtubeLink = meal.meals[0].strYoutube!!
        }
    }

    private fun setInfoInViews(){
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)
        binding.collapsingToolbar.title = mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    private fun getMealInfoFromIntent(){
        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }

    private fun loadingCase(){
        binding.progressBar.visibility = View.VISIBLE
        binding.btnSave.visibility = View.INVISIBLE
        binding.tvInstructions.visibility = View.INVISIBLE
        binding.tvCategoryInfo.visibility = View.INVISIBLE
        binding.tvAreaInfo.visibility = View.INVISIBLE
        binding.imgYoutube.visibility = View.INVISIBLE

    }

    private fun onResponseCase(){
        binding.progressBar.visibility = View.INVISIBLE
        binding.btnSave.visibility = View.VISIBLE
        binding.tvInstructions.visibility = View.VISIBLE
        binding.tvCategoryInfo.visibility = View.VISIBLE
        binding.tvAreaInfo.visibility = View.VISIBLE
        binding.imgYoutube.visibility = View.VISIBLE
    }
}