package com.niharikainala.yummytummy.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.niharikainala.yummytummy.databinding.PopularItemsBinding
import com.niharikainala.yummytummy.pojo.Category
import com.niharikainala.yummytummy.pojo.Meal
import com.niharikainala.yummytummy.pojo.MealsByCategoryList

class MostPopularAdapter():RecyclerView.Adapter<MostPopularAdapter.PopularMealViewHolder>() {
    private lateinit var onItemClick: OnItemClick
    private var mealList : List<Meal> = ArrayList()

    fun setMeals(mealsList:List<Meal>){
        this.mealList = mealsList
        notifyDataSetChanged()
    }
    fun setOnClickListener(onItemClick: OnItemClick){
        this.onItemClick = onItemClick
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
        return PopularMealViewHolder(PopularItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false
        ))
    }

    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealList[position].strMealThumb)
            .into(holder.binding.imgPopularMealItem)

        holder.itemView.setOnClickListener {
            onItemClick.onItemClick(mealList[position])
        }
    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    class PopularMealViewHolder(val binding:PopularItemsBinding):RecyclerView.ViewHolder(binding.root){

    }

    interface OnItemClick{
        fun onItemClick(meal: Meal)
    }
}