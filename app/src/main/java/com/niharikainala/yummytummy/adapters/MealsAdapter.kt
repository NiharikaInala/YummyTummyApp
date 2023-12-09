package com.niharikainala.yummytummy.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.niharikainala.yummytummy.databinding.MealItemBinding
import com.niharikainala.yummytummy.data.pojo.Meal
import com.niharikainala.yummytummy.data.pojo.MealDetail

class MealsAdapter: RecyclerView.Adapter<MealsAdapter.FavoriteMealsViewHolder>() {

     private lateinit var onMealClick: OnMealClick

    private val diffUtil = object : DiffUtil.ItemCallback<MealDetail>(){
        override fun areItemsTheSame(oldItem: MealDetail, newItem: MealDetail): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: MealDetail, newItem: MealDetail): Boolean {
            return oldItem == newItem
        }

    }

    fun onMealClick(onMealClick:OnMealClick){
        this.onMealClick = onMealClick

    }

    val differ = AsyncListDiffer(this,diffUtil)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MealsAdapter.FavoriteMealsViewHolder {
    return FavoriteMealsViewHolder(MealItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(
        holder: MealsAdapter.FavoriteMealsViewHolder,
        position: Int
    ) {

    val meal = differ.currentList[position]
        Glide.with(holder.itemView)
            .load(meal.strMealThumb)
            .into(holder.binding.imgMeal)
        holder.binding.tvMealName.text = meal.strMeal

        holder.itemView.setOnClickListener {
            onMealClick.onMealClick(meal)
        }
    }

    override fun getItemCount(): Int {
    return differ.currentList.size
    }

    inner class FavoriteMealsViewHolder(val binding:MealItemBinding):RecyclerView.ViewHolder(binding.root)

    interface OnMealClick{
        fun onMealClick(meal: MealDetail)
    }

}