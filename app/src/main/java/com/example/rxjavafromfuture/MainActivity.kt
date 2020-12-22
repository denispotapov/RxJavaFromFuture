package com.example.rxjavafromfuture

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import timber.log.Timber
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.makeQuery().observe(this, Observer {
            Timber.d("onChange: This is LiveData response")
            try {
                Timber.d("onChange: ${it.string()}")
            } catch (e: IOException) {
                e.printStackTrace()
            }
        })
    }
}