package com.niharikainala.yummytummy.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.niharikainala.yummytummy.R
import com.niharikainala.yummytummy.ui.activities.CategoryMealActivity
import com.niharikainala.yummytummy.ui.activities.MainActivity
import com.niharikainala.yummytummy.adapters.CategoriesAdapter
import com.niharikainala.yummytummy.databinding.FragmentCategoriesBinding
import com.niharikainala.yummytummy.ui.fragments.HomeFragment.Companion.CATEGORY_NAME
import com.niharikainala.yummytummy.data.pojo.Category
import com.niharikainala.yummytummy.viewmodel.HomeViewModel


class CategoriesFragment : Fragment() {
    private lateinit var binding:FragmentCategoriesBinding
    private lateinit var categoriesAdapter : CategoriesAdapter
    private lateinit var homeMvvm : HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeMvvm = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCategoriesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecylerView()
        observeCategoriesLiveData()
        onCategoryMealClick()
    }

    private fun onCategoryMealClick() {
        categoriesAdapter.onItemClicked(object : CategoriesAdapter.OnItemCategoryClicked{
            override fun onClickListener(category: Category) {
                val intent = Intent(activity, CategoryMealActivity::class.java)
                intent.putExtra(CATEGORY_NAME, category.strCategory)
                startActivity(intent)
            }

        })
    }

    private fun observeCategoriesLiveData(){
        homeMvvm.observeCategoryMealLiveData().observe(viewLifecycleOwner, Observer {
            categories -> categoriesAdapter.setCategoryList(categories)
        })
    }

    private fun prepareRecylerView(){
        categoriesAdapter = CategoriesAdapter()
        binding.categoriesRecyclerView.apply {
            layoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter = categoriesAdapter

        }
    }
}