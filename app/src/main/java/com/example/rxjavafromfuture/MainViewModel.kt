package com.example.rxjavafromfuture

import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import okhttp3.ResponseBody
import java.util.concurrent.Future

class MainViewModel : ViewModel() {

    private val repository = Repository().getInstance()

    fun makeFutureQuery(): Future<Observable<ResponseBody>> {
        return repository?.makeFutureQuery()!!
    }
}