package com.example.rxjavafromfuture

import com.example.rxjavafromfuture.request.ServiceGenerator
import io.reactivex.Observable
import okhttp3.ResponseBody
import java.util.concurrent.*


class Repository {

    private var instance: Repository? = null

    fun getInstance(): Repository? {
        if (instance == null) {
            instance = Repository()
        }
        return instance
    }

    fun makeFutureQuery(): Future<Observable<ResponseBody>> {

        val executor = Executors.newSingleThreadExecutor()

        val myNetworkCallable = object: Callable<Observable<ResponseBody>> {
            override fun call(): Observable<ResponseBody> {
                val serviceGenerator = ServiceGenerator()
                return serviceGenerator.getRequestApi().makeObservableQuery()
            }
        }

        return object : Future<Observable<ResponseBody>> {
            override fun cancel(p0: Boolean): Boolean {
                if (p0) {
                    executor.isShutdown
                }
                return false
            }

            override fun isCancelled(): Boolean {
                return executor.isShutdown
            }

            override fun isDone(): Boolean {
                return executor.isTerminated
            }

            override fun get(): Observable<ResponseBody> {
                return executor.submit(myNetworkCallable).get()
            }

            override fun get(p0: Long, p1: TimeUnit?): Observable<ResponseBody> {
                return executor.submit(myNetworkCallable).get(p0, p1)
            }
        }
    }
}