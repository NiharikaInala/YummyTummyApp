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
import com.niharikainala.yummytummy.activities.CategoryMealActivity
import com.niharikainala.yummytummy.activities.MainActivity
import com.niharikainala.yummytummy.activities.MealActivity
import com.niharikainala.yummytummy.adapters.CategoriesAdapter
import com.niharikainala.yummytummy.adapters.MostPopularAdapter
import com.niharikainala.yummytummy.databinding.FragmentHomeBinding
import com.niharikainala.yummytummy.pojo.Category
import com.niharikainala.yummytummy.pojo.Meal
import com.niharikainala.yummytummy.pojo.MealList
import com.niharikainala.yummytummy.pojo.MealsByCategoryList
import com.niharikainala.yummytummy.viewmodel.HomeViewModel


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeMvvm: HomeViewModel
    private lateinit var randomMeal: MealList
    private lateinit var popularItemsAdapter: MostPopularAdapter
    private lateinit var categoryMealAdapter: CategoriesAdapter

    companion object {
        const val MEAL_ID = "com.niharikainala.yummytummy.fragments.idMeal"
        const val MEAL_NAME = "com.niharikainala.yummytummy.fragments.nameMeal"
        const val MEAL_THUMB = "com.niharikainala.yummytummy.fragments.thumbMeal"
        const val CATEGORY_NAME = "com.niharikainala.yummytummy.fragments.categoryName"
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

        homeMvvm.getPopularItems("beef")
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
        categoryMealAdapter.onItemClicked(object : CategoriesAdapter.OnItemCategoryClicked{
            override fun onClickListener(category: Category) {
                val intent = Intent(activity, CategoryMealActivity::class.java)
                intent.putExtra(CATEGORY_NAME, category.strCategory)
                startActivity(intent)
            }

        })
    }

    private fun onPopularItemClick() {
        popularItemsAdapter.setOnClickListener(
            object : MostPopularAdapter.OnItemClick {
                override fun onItemClick(meal: Meal) {
                    val intent = Intent(activity, MealActivity::class.java)
                    intent.putExtra(MEAL_ID, meal.idMeal)
                    intent.putExtra(MEAL_NAME, meal.strMeal)
                    intent.putExtra(MEAL_THUMB, meal.strMealThumb)
                    startActivity(intent)
                }

            })
    }

    private fun onRandomMealClick() {
        binding.randomMeal.setOnClickListener {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, randomMeal.meals[0].idMeal)
            intent.putExtra(MEAL_NAME, randomMeal.meals[0].strMeal)
            intent.putExtra(MEAL_THUMB, randomMeal.meals[0].strMealThumb)
            startActivity(intent)
        }
    }

    private fun observeRandomMeal() {
        homeMvvm.observeRandomMealLivedata().observe(viewLifecycleOwner, object : Observer<MealList> {
            override fun onChanged(value: MealList) {
                Glide.with(this@HomeFragment)
                    .load(value.meals[0].strMealThumb)
                    .into(binding.imgRandomMeal)
                this@HomeFragment.randomMeal = value
            }
        })
    }

    private fun observePopularItemLiveData() {
        homeMvvm.observePopularMealLiveData()
            .observe(viewLifecycleOwner, object : Observer<MealsByCategoryList> {
                override fun onChanged(value: MealsByCategoryList) {
                    val meals = value.meals
                    setMealsByCategoryAdapter(meals)
                }
            })
    }
    private fun setMealsByCategoryAdapter(meals: List<Meal>) {
        popularItemsAdapter.setMeals(meals)
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