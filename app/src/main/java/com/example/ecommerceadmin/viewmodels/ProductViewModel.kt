package com.example.ecommerceadmin.viewmodels

import androidx.lifecycle.ViewModel
import com.example.ecommerceadmin.repos.ProductRepository

class ProductViewModel : ViewModel() {
    val repository = ProductRepository()
    fun getCategories() = repository.getAllCategories()


}