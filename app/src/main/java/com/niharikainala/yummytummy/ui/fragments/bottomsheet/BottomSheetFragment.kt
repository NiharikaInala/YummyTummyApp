package com.niharikainala.yummytummy.ui.fragments.bottomsheet

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.niharikainala.yummytummy.R
import com.niharikainala.yummytummy.ui.activities.MainActivity
import com.niharikainala.yummytummy.ui.activities.MealActivity
import com.niharikainala.yummytummy.databinding.FragmentBottomSheetBinding
import com.niharikainala.yummytummy.ui.fragments.HomeFragment
import com.niharikainala.yummytummy.viewmodel.HomeViewModel

private const val MEAL_ID = "param1"



class BottomSheetFragment : BottomSheetDialogFragment() {
    private var mealId: String? = null
    private var mealName:String?=null
    private var mealThumb:String?=null
    private lateinit var binding : FragmentBottomSheetBinding
    private lateinit var mvvm : HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mealId = it.getString(MEAL_ID)
        }
        mvvm = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBottomSheetBinding.inflate(inflater)
        return binding.root}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mealId?.let {
            mvvm.getMealById(it)
        }
        observeBottomMealData()
        onBottomSheetDialogClick()
    }



    private fun onBottomSheetDialogClick(){
        binding.bottomSheet.setOnClickListener {
            if(mealName!=null&&mealThumb!=null){
                val bundle = Bundle()
                bundle.apply {

                }
                val intent = Intent(activity, MealActivity::class.java)
                intent.apply {
                    putExtra(HomeFragment.MEAL_ID,mealId)
                    putExtra(HomeFragment.MEAL_NAME,mealName)
                    putExtra(HomeFragment.MEAL_THUMB,mealThumb)
                }
                startActivity(intent)
            }
        }
    }

    private fun observeBottomMealData(){
        mvvm.observeBottomMealLiveData().observe(viewLifecycleOwner, Observer {meal->
            Glide.with(this).load(meal.strMealThumb).into(binding.imgCategory)
            binding.tvAreaBttmsheet.text = meal.strArea
            binding.tvCategoryBttmsheet.text = meal.strCategory
            binding.tvCategoryBtmsheetName.text = meal.strMeal
            mealName = meal.strMeal
            mealThumb = meal.strMealThumb

        })
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            BottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(MEAL_ID, param1)

                }
            }
    }
}