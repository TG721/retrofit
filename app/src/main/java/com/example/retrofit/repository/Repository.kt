package com.example.retrofit.repository

import com.example.retrofit.MyDataItem
import com.example.retrofit.RetrofitInstance
import retrofit2.Response

class Repository {
    suspend fun getPost(): Response<List<MyDataItem>>{
        return RetrofitInstance.api.getData()
    }
}