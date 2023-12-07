package com.niharikainala.yummytummy.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.niharikainala.yummytummy.R
import com.niharikainala.yummytummy.activities.MainActivity
import com.niharikainala.yummytummy.adapters.CategoriesAdapter
import com.niharikainala.yummytummy.databinding.FragmentCategoriesBinding
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