package com.manmohan.nasaapod.di.module


import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.manmohan.nasaapod.ApodApp
import com.manmohan.nasaapod.data.api.ApiConstants
import com.manmohan.nasaapod.data.api.ApiEndPoint
import com.manmohan.nasaapod.di.scope.AppScope
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.time.Clock
import java.util.*
import java.util.concurrent.TimeUnit.SECONDS
import javax.inject.Singleton


@Module
class ApiModule {
    val DISK_CACHE_SIZE :Long = 50 * 1024 * 1024  // 50MB


    @AppScope
    @Provides
    fun createOkHttpClient(app: ApodApp): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .connectTimeout(10, SECONDS)
            .readTimeout(10, SECONDS)
            .writeTimeout(10, SECONDS)

        // Logging interceptor
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        okHttpClientBuilder.addInterceptor(httpLoggingInterceptor)

        // Install an HTTP cache in the application cache directory.
        val cacheDir = File(app.cacheDir, "http")
        val cache = Cache(cacheDir, DISK_CACHE_SIZE)
        okHttpClientBuilder.cache(cache)

        return okHttpClientBuilder.build()
    }

    @Provides
    @AppScope
    internal fun provideConnectivityManager(app: ApodApp): ConnectivityManager {
        return app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    @AppScope
    @Provides
    internal fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd").create()
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(ApiConstants.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }


    @Provides
    @AppScope
    internal fun provideToday(): Date {
        return Date(System.currentTimeMillis())
    }
    @AppScope
    @Provides
    internal fun provideNewsApi(retrofit: Retrofit): ApiEndPoint {
        return retrofit.create(ApiEndPoint::class.java)
    }
}
