package com.example.fakegpsmap.base.backend

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

abstract class BaseBackendService : IBackendService {

    override fun <T> createService(api: Class<T>): T {
        return createService(api, emptyList())
    }

    override fun <T> createService(api: Class<T>, interceptor: Interceptor): T {
        return createService(api, listOf(interceptor))
    }

    override fun <T> createService(api: Class<T>, interceptors: List<Interceptor>): T {
        var httpClient = OkHttpClient.Builder()
        setHttpDefaultClient(httpClient)
        httpClient.addInterceptor(setDefaultInterceptor())
        interceptors?.forEach {
            httpClient.addInterceptor(it)
        }
        var okHttpClinet = httpClient.build()
        var retrofit = Retrofit.Builder()
            //设置数解析器,暂设为Gson
            .addConverterFactory(GsonConverterFactory.create())
            //网络请求适配器支持RXJava
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            //okHttp
            .client(okHttpClinet)
            .baseUrl(baseUrl)
            .build()
        return retrofit.create(api)
    }

    //baseUrl
    abstract val baseUrl: String

    /**
     * 设置默认httpClient属性
     */
    abstract fun setHttpDefaultClient(httpClient: OkHttpClient.Builder)

    /**
     * 设置默认拦截器
     */
    abstract fun setDefaultInterceptor(): Interceptor
}