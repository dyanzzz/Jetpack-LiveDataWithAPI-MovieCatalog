package com.example.livedatawithapi

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.*

class RestaurantRepository {
    fun getRestaurantById(restaurantId: String): LiveData<Restaurant>{
        val restaurant = MutableLiveData<Restaurant>()

        val client = ApiConfig.getApiService().getRestaurant(restaurantId)
        client.enqueue(object : Callback<RestaurantResponse> {
            override fun onResponse(
                call: Call<RestaurantResponse>,
                response: Response<RestaurantResponse>
            ){
                if(response.isSuccessful){
                    restaurant.postValue(response.body()?.restaurant)
                }else{
                    Log.e("MainViewModel", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<RestaurantResponse>, t: Throwable){
                Log.e("MainViewModel", "onFailure: ${t.message.toString()}" )
            }
        })

        return restaurant
    }
}