package com.example.testapp03

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testapp03.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        var flag = false

        binding.button.setOnClickListener {
            if (flag ) {
                binding.textview.text = getString(R.string.hello)
                flag = false
            } else {
                binding.textview.text = getString(R.string.world)
                flag = true
            }
        }
    }
}