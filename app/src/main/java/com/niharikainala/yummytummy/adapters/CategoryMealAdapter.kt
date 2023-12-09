package com.niharikainala.yummytummy.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.niharikainala.yummytummy.databinding.MealItemBinding
import com.niharikainala.yummytummy.data.pojo.Meal

class CategoryMealAdapter:RecyclerView.Adapter<CategoryMealAdapter.CategoryMealViewHolder>() {
    var mealList = ArrayList<Meal>()
    private lateinit var onMealClick: OnMealClick

    fun setMealList(mealList:List<Meal>){
        this.mealList = mealList as ArrayList<Meal>
        notifyDataSetChanged()
    }

    fun setMealClickListner(onMealClick: OnMealClick){
        this.onMealClick = onMealClick
    }
    inner class CategoryMealViewHolder(val binding:MealItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealViewHolder {
        return CategoryMealViewHolder(
            MealItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    override fun onBindViewHolder(holder: CategoryMealViewHolder, position: Int) {
       Glide.with(holder.itemView)
           .load(mealList[position].strMealThumb)
           .into(holder.binding.imgMeal)
        holder.binding.tvMealName.text = mealList[position].strMeal

        holder.itemView.setOnClickListener {
            onMealClick.onMealClick(mealList[position])
        }
    }

    interface OnMealClick{
        fun onMealClick(meal: Meal)
    }

}