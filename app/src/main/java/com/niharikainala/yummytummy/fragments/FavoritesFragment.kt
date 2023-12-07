package com.niharikainala.yummytummy.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.Snackbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
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

        val itemTouchHelper = object :ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN,ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT)
        {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            )= true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                viewModel.deleteMeal(favoriteMealsAdapter.differ.currentList[position])
                Snackbar.make(requireView(),"Meal deleted",Snackbar.LENGTH_LONG).setAction(
                    "Undo",View.OnClickListener {
                        viewModel.insertMeal(favoriteMealsAdapter.differ.currentList[position])
                    }
                ).show()
            }

        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.favRecView)
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