package com.example.fakegpsmap.base.backend

import okhttp3.Interceptor

interface IBackendService {
    fun <T> createService(api: Class<T>): T
    fun <T> createService(api: Class<T>, interceptor: Interceptor): T
    fun <T> createService(api: Class<T>, interceptors: List<Interceptor>): T
}