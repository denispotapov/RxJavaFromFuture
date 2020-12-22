package com.example.rxjavafromfuture

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import timber.log.Timber
import java.io.IOException
import java.util.concurrent.ExecutionException

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        try {
            viewModel.makeFutureQuery().get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResponseBody> {
                    override fun onSubscribe(d: Disposable) {
                        Timber.d("onSubscribe: called.")
                    }

                    override fun onNext(t: ResponseBody) {
                        Timber.d("onNext: got the response from server!")
                        try {
                            Timber.d("onNext: ${t.string()}")
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }

                    override fun onError(e: Throwable) {
                        Timber.e("onError: $e");
                    }

                    override fun onComplete() {
                        Timber.d("onComplete: called.");
                    }
                })

        } catch (e: InterruptedException) {
            e.printStackTrace()
        } catch (e: ExecutionException) {
            e.printStackTrace()
        }
    }
}