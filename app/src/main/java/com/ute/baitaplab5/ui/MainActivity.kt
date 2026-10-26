package com.ute.baitaplab5.ui

import android.content.Intent
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

    // Bài 1: Chống click đúp và gửi dữ liệu sang màn hình Detail
    private fun setupClickDebounce() {
        binding.btnSubmit.setOnClickListener {
            val currentTime = SystemClock.elapsedRealtime()
            if (currentTime - lastClickTime < CLICK_DEBOUNCE_DELAY) {
                return@setOnClickListener
            }
            lastClickTime = currentTime

            Toast.makeText(this, "Đã tiếp nhận yêu cầu!", Toast.LENGTH_SHORT).show()

            // Tạo Intent và chuyển màn hình tại đây
            val intent = Intent(this, Detail::class.java).apply {
                putExtra("name", binding.etFullName.text.toString())
                putExtra("email", binding.etEmail.text.toString())
                putExtra("gender", when (binding.rgGender.checkedRadioButtonId) {
                    R.id.rbMale -> "Nam"
                    R.id.rbFemale -> "Nữ"
                    else -> "Khác"
                })
            }
            startActivity(intent)
        }
    }

    // Bài 2: Tự động xóa lỗi khi người dùng nhập ký tự
    private fun setupRealtimeValidation() {
        binding.etFullName.doAfterTextChanged {
            if (binding.tilFullName.error != null) {
                binding.tilFullName.error = null
                binding.tilFullName.isErrorEnabled = false
            }
        }

        binding.etEmail.doAfterTextChanged {
            if (binding.tilEmail.error != null) {
                binding.tilEmail.error = null
                binding.tilEmail.isErrorEnabled = false
            }
        }

        binding.etPassword.doAfterTextChanged {
            if (binding.tilPassword.error != null) {
                binding.tilPassword.error = null
                binding.tilPassword.isErrorEnabled = false
            }
        }
    }

    // Bài 3: Bắt sự kiện RadioGroup và CheckBox
    private fun setupSelectionListeners() {
        binding.rgGender.setOnCheckedChangeListener { _, checkedId ->
            val gender = when (checkedId) {
                R.id.rbMale -> "Nam"
                R.id.rbFemale -> "Nữ"
                else -> "Khác"
            }
            Log.d("Register", "Giới tính đã chọn: $gender")
        }

        binding.cbTerms.setOnCheckedChangeListener { _, isChecked ->
            binding.btnSubmit.isEnabled = isChecked
        }
    }
}