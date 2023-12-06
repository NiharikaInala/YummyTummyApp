package com.niharikainala.yummytummy.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.niharikainala.yummytummy.R
import com.niharikainala.yummytummy.activities.MainActivity
import com.niharikainala.yummytummy.adapters.FavoriteMealsAdapter
import com.niharikainala.yummytummy.databinding.FragmentFavoritesBinding
import com.niharikainala.yummytummy.viewmodel.HomeViewModel


class FavoritesFragment : Fragment() {
private lateinit var binding:FragmentFavoritesBinding
private lateinit var viewModel:HomeViewModel
private lateinit var favoriteMealsAdapter: FavoriteMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    viewModel = (activity as MainActivity).viewModel
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecyclerView()
        observeFavorites()
    }

    private fun prepareRecyclerView(){
        favoriteMealsAdapter = FavoriteMealsAdapter()
        binding.favRecView.apply{
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter = favoriteMealsAdapter
        }
    }

    private fun observeFavorites(){
        viewModel.observeFavMealsLiveData().observe(requireActivity(), Observer {
            meals -> meals.forEach{
            favoriteMealsAdapter.differ.submitList(meals)
        }
        })
    }


}