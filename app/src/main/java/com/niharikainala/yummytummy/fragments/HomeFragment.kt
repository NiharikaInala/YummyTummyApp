package com.niharikainala.yummytummy.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.niharikainala.yummytummy.activities.MainActivity
import com.niharikainala.yummytummy.activities.MealActivity
import com.niharikainala.yummytummy.adapters.CategoriesAdapter
import com.niharikainala.yummytummy.adapters.MostPopularAdapter
import com.niharikainala.yummytummy.databinding.FragmentHomeBinding
import com.niharikainala.yummytummy.pojo.Category
import com.niharikainala.yummytummy.pojo.MealsByCategory
import com.niharikainala.yummytummy.pojo.Meal
import com.niharikainala.yummytummy.viewmodel.HomeViewModel


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeMvvm: HomeViewModel
    private lateinit var randomMeal: Meal
    private lateinit var popularItemsAdapter: MostPopularAdapter
    private lateinit var categoryMealAdapter: CategoriesAdapter

    companion object {
        const val MEAL_ID = "com.niharikainala.yummytummy.fragments.idMeal"
        const val MEAL_NAME = "com.niharikainala.yummytummy.fragments.nameMeal"
        const val MEAL_THUMB = "com.niharikainala.yummytummy.fragments.thumbMeal"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeMvvm = ViewModelProviders.of(this)[HomeViewModel::class.java]
        popularItemsAdapter = MostPopularAdapter()
        categoryMealAdapter = CategoriesAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preparePouplarItemsRecyclerView()
        prepareCategoryRecyclerView()


        homeMvvm.getRandomMeal()
        observeRandomMeal()
        onRandomMealClick()

        homeMvvm.getPopularItems("seafood")
        observePopularItemLiveData()
        onPopularItemClick()

        homeMvvm.getCategoryMeals()
        observeCategoryMealLiveData()
        onCategoryMealClick()


    }

    private fun observeCategoryMealLiveData() {
        homeMvvm.observeCategoryMealLiveData()
            .observe(viewLifecycleOwner, object : Observer<List<Category>> {
                override fun onChanged(value: List<Category>) {
                    categoryMealAdapter.setCategoryList(value)
                }
            })
    }

    private fun onCategoryMealClick() {

    }

    private fun onPopularItemClick() {
        popularItemsAdapter.onItemClick = { meal ->
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, meal.idCategory)
            intent.putExtra(MEAL_NAME, meal.strCategory)
            intent.putExtra(MEAL_THUMB, meal.strCategoryThumb)
            startActivity(intent)
        }
    }

    private fun onRandomMealClick() {
        binding.randomMeal.setOnClickListener {
            val intent = Intent(activity, MainActivity::class.java)
            intent.putExtra(MEAL_ID, randomMeal.idMeal)
            intent.putExtra(MEAL_NAME, randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB, randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observeRandomMeal() {
        homeMvvm.observeRandomMealLivedata().observe(viewLifecycleOwner, object : Observer<Meal> {
            override fun onChanged(value: Meal) {
                Glide.with(this@HomeFragment)
                    .load(value.strMealThumb)
                    .into(binding.imgRandomMeal)
                this@HomeFragment.randomMeal = value
            }
        })
    }

    private fun observePopularItemLiveData() {
        homeMvvm.observePopularMealLiveData()
            .observe(viewLifecycleOwner, object : Observer<List<MealsByCategory>> {
                override fun onChanged(value: List<MealsByCategory>) {
                    popularItemsAdapter.setMeals(value as ArrayList)
                }
            })
    }

    private fun preparePouplarItemsRecyclerView() {
        binding.recViewMealsPopular.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularItemsAdapter

        }
    }

    private fun prepareCategoryRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoryMealAdapter
        }
    }


}