package com.niharikainala.yummytummy.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.niharikainala.yummytummy.R
import com.niharikainala.yummytummy.ui.activities.MainActivity
import com.niharikainala.yummytummy.ui.activities.MealActivity
import com.niharikainala.yummytummy.adapters.MealsAdapter
import com.niharikainala.yummytummy.databinding.FragmentSearchBinding
import com.niharikainala.yummytummy.ui.fragments.HomeFragment.Companion.MEAL_ID
import com.niharikainala.yummytummy.ui.fragments.HomeFragment.Companion.MEAL_NAME
import com.niharikainala.yummytummy.ui.fragments.HomeFragment.Companion.MEAL_THUMB
import com.niharikainala.yummytummy.data.pojo.MealDetail
import com.niharikainala.yummytummy.viewmodel.HomeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var mvvm: HomeViewModel
    private lateinit var mealsAdapter: MealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mvvm = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecyclerView()
        binding.icSearch.setOnClickListener {
            searchMeals()
        }
        observeSearchLiveData()
        onMealClick()

        var searchJob: Job? = null
        binding.edSearch.addTextChangedListener {
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                delay(500)
                mvvm.searchMeals(it.toString())

            }
        }

    }

    private fun onMealClick(){
        mealsAdapter.onMealClick(object : MealsAdapter.OnMealClick{
            override fun onMealClick(meal: MealDetail) {
                val intent = Intent(activity, MealActivity::class.java)
                intent.putExtra(MEAL_ID, meal.idMeal)
                intent.putExtra(MEAL_NAME, meal.strMeal)
                intent.putExtra(MEAL_THUMB, meal.strMealThumb)
                startActivity(intent)
            }

        })
    }

    private fun observeSearchLiveData() {
        mvvm.observeSearchMealLiveData().observe(viewLifecycleOwner, Observer { mealList ->
            mealsAdapter.differ.submitList(mealList)
        })
    }

    private fun searchMeals() {
        val searchQuery = binding.edSearch.text.toString()
        if (searchQuery.isNotEmpty()) {
            mvvm.searchMeals(searchQuery)
        }
    }

    private fun prepareRecyclerView() {
        mealsAdapter = MealsAdapter()
        binding.rvSearchedMeal.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = mealsAdapter
        }
    }

}