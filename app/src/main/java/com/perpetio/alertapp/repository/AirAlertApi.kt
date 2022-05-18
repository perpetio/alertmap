package com.perpetio.alertapp.repository

import com.perpetio.alertapp.data_models.StatesInfoModel
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


private const val BASE_URL = "https://alerts.com.ua/"

private val apiService: AirAlertApi by lazy {
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request: Request =
                chain.request().newBuilder().addHeader(
                    API_KEY_HEADER, API_KEY
                ).build()
            chain.proceed(request)
        }
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    retrofit.create(AirAlertApi::class.java)
}

fun getAlertApiService() = apiService

interface AirAlertApi {
    @GET("/api/states")
    suspend fun getStates(): StatesInfoModel
}