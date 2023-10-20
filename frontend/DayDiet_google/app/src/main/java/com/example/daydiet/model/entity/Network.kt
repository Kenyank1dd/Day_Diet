package com.example.daydiet.model.entity

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

object Network {
    private const val baseUrl =
        "http://124.221.166.194:80"
//    "http://172.25.153.154:8080"
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(
            MoshiConverterFactory.create(
                Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()
            )
        ).build()

    fun <T>createService(clazz: Class<T>): T {

        return retrofit.create(clazz)
    }
}

