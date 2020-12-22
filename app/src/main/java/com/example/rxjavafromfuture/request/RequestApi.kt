package com.example.rxjavafromfuture.request

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET

interface RequestApi {

    @GET("todos/1")
    fun makeObservableQuery(): Observable<ResponseBody>

}