package com.example.ecommerceadmin.viewmodels

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ecommerceadmin.models.Product
import com.example.ecommerceadmin.models.Purchase
import com.example.ecommerceadmin.repos.ProductRepository
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream

class ProductViewModel : ViewModel() {
    val repository = ProductRepository()
    val statusLD = MutableLiveData<String>()

    fun getCategories() = repository.getAllCategories()
    fun addProduct(product: Product, purchase: Purchase) {
        repository.addProduct(product, purchase) {
            statusLD.value = it
        }
    }
    fun getProducts() = repository.getAllProducts()

    fun uploadImage(bitmap: Bitmap, callback: (String) -> Unit) {
        val photoRef = FirebaseStorage.getInstance().reference
            .child("images/${System.currentTimeMillis()}")
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, baos)
        val data: ByteArray = baos.toByteArray()
        val uploadTask = photoRef.putBytes(data)
        val urlTask = uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            photoRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result.toString()
                callback(downloadUri)
            } else {
                // Handle failures
                // ...
            }
        }
    }
}