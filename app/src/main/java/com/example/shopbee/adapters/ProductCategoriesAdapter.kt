package com.example.shopbee.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.shopbee.databinding.ProductItemLayoutBinding
import com.example.shopbee.response.data.Product

class ProductCategoriesAdapter: RecyclerView.Adapter<ProductCategoriesAdapter.ProductCategoriesViewHolder>() {

    private var products = mutableListOf<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductCategoriesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ProductItemLayoutBinding.inflate(inflater, parent, false)
        return ProductCategoriesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductCategoriesViewHolder, position: Int) {
      val product = products[position]
        holder.bind(product)
    }

    override fun getItemCount() = products.size

    fun setProductCategories(product: List<Product>){
        this.products = product.toMutableList()
        notifyDataSetChanged()
    }

    inner class ProductCategoriesViewHolder(val binding: ProductItemLayoutBinding) : ViewHolder(binding.root){
        fun bind(product: Product) {
            binding.apply {
                Glide.with(binding.root).load(product.image).into(prodItmImg)
                prodItmTxt.text = product.title
            }
        }

    }

}