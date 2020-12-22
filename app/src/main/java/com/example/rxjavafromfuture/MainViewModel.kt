package com.example.rxjavafromfuture

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import okhttp3.ResponseBody
import java.util.concurrent.Future

class MainViewModel : ViewModel() {

    private val repository = Repository().getInstance()

    fun makeQuery(): LiveData<ResponseBody> {
        return repository?.makeReactiveQuery()!!
    }
}