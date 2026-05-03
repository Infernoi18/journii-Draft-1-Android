package com.example.journii

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.journii.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.titleText.text = intent.getStringExtra("title")
        binding.contentText.text = intent.getStringExtra("content")
        binding.categoryText.text = intent.getStringExtra("category")
    }
}