package com.ute.baitaplab5.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ute.baitaplab5.databinding.ActivityDetailBinding

class Detail : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvDetailFullName.text = intent.getStringExtra("name")
        binding.tvDetailEmail.text = intent.getStringExtra("email")
        binding.tvDetailPhone.text = intent.getStringExtra("phone")
        binding.tvDetailGender.text = intent.getStringExtra("gender")

        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}