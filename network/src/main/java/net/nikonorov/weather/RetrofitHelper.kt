package net.nikonorov.weather

import net.nikonorov.weather.result.ResultCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    private const val baseUrl = "https://api.open-meteo.com/"

    fun getInstance(): Retrofit {
        val client = OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor()).build()
        return Retrofit.Builder().baseUrl(baseUrl).client(client)
            .addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(
                ResultCallAdapterFactory()
            ).build()
    }
}
