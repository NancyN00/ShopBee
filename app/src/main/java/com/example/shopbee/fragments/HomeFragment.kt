package com.example.shopbee.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopbee.adapters.ProductCategoriesAdapter
import com.example.shopbee.databinding.FragmentHomeBinding
import com.example.shopbee.response.repository.GetRepositoryImplRepository
import com.example.shopbee.viewmodel.HomeViewModel
import com.example.shopbee.viewmodel.HomeViewModelFactory
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding
    private lateinit var viewModel : HomeViewModel
    private lateinit var adapter: ProductCategoriesAdapter
    private lateinit var viewModelFactory: HomeViewModelFactory


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository  = GetRepositoryImplRepository()
        val viewModelFactory = HomeViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)

        setUpUi()
        getProducts()

    }

    private fun setUpUi() {
        adapter = ProductCategoriesAdapter()
        binding.productCateRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.productCateRv.adapter = adapter
    }

    private fun getProducts() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { productstate ->
                    if (productstate.isLoading) {
                        binding.progressbarProductCate.visibility = View.GONE
                    }

                    if (productstate.products.isNotEmpty()) {
                        binding.productCateRv.visibility = View.GONE
                        adapter.setProductCategories(productstate.products)
                    }

                    if (productstate.message.isNotBlank()) {
                        binding.productCateRv.visibility = View.GONE
                        Toast.makeText(context, productstate.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }




}