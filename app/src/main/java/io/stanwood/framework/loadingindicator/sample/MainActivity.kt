package io.stanwood.framework.loadingindicator.sample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import io.stanwood.framework.loadingindicator.sample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        binding.viewModel = MainActivityViewModel()
        setContentView(binding.root)
    }
}
