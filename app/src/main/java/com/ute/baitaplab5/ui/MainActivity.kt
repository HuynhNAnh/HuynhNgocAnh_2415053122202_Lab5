package com.ute.baitaplab5.ui

import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.ute.baitaplab5.R
import com.ute.baitaplab5.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var lastClickTime: Long = 0L
    private val CLICK_DEBOUNCE_DELAY = 1000L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnSubmit.isEnabled = binding.cbTerms.isChecked

        setupClickDebounce()
        setupRealtimeValidation()
        setupSelectionListeners()
        }
    private fun setupClickDebounce() {
        binding.btnSubmit.setOnClickListener {
            val currentTime = SystemClock.elapsedRealtime()
            if (currentTime - lastClickTime < CLICK_DEBOUNCE_DELAY) {
                return@setOnClickListener
            }
            lastClickTime = currentTime

            Toast.makeText(this, "Đã tiếp nhận yêu cầu!", Toast.LENGTH_SHORT).show()
        }
    }
    
    }
}