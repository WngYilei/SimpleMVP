package com.xl.base.net

import androidx.core.util.Pair
import com.google.gson.GsonBuilder
import com.xl.base.BuildConfig
import com.zity.sunmi_t1.api.CashConverterFactory
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.*
import java.util.concurrent.TimeUnit


object ApiManager {

    var retrofit: Retrofit

    init {
        val okHttpClientBuilder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            okHttpClientBuilder.addInterceptor(logging)

            okHttpClientBuilder.addNetworkInterceptor(logging)

        } else {
            val logInterceptor = HttpLoggingInterceptor(HttpLogger())
            logInterceptor.level = HttpLoggingInterceptor.Level.BODY
            okHttpClientBuilder.addInterceptor(logInterceptor)
        }

        val logInterceptor = HttpLoggingInterceptor(HttpLogger())
        logInterceptor.level = HttpLoggingInterceptor.Level.BODY
        okHttpClientBuilder.addInterceptor(logInterceptor)

        okHttpClientBuilder.connectTimeout(10, TimeUnit.SECONDS)
        okHttpClientBuilder.writeTimeout(10, TimeUnit.SECONDS)
        okHttpClientBuilder.readTimeout(10, TimeUnit.SECONDS)
        okHttpClientBuilder.retryOnConnectionFailure(false)

        retrofit = Retrofit.Builder()
            .baseUrl("https://www.wanandroid.com")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(createGSonConverter())
            .client(okHttpClientBuilder.build()).build()
        RxJava2CallAdapterFactory.create()
    }

    fun <T> provideApiService(clazz: Class<T>): T = retrofit.create(clazz)


    private fun createGSonConverter(): Converter.Factory {
        val builder = GsonBuilder().serializeNulls()
        return CashConverterFactory.create(builder.create())
    }

    fun genBody(vararg params: Pair<String, Any>): RequestBody {
        val json = JSONObject()
        params.forEach {
            json.put(it.first, it.second)
        }
        return RequestBody.create(MediaType.parse("application/json"), json.toString())
    }

    fun genBody(json: JSONObject): RequestBody {
        return RequestBody.create(MediaType.parse("application/json"), json.toString())
    }

    fun genBody(req: String): RequestBody {
        return RequestBody.create(MediaType.parse("application/json"), req)
    }

    fun genJsonBody(vararg params: Pair<String, Any>): JSONObject {
        val json = JSONObject()
        params.forEach {
            json.put(it.first, it.second)
        }
        return json
    }

}