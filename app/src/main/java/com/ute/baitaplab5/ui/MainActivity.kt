package com.ute.baitaplab5.ui

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import androidx.core.widget.doAfterTextChanged
import com.ute.baitaplab5.R
import com.ute.baitaplab5.databinding.ActivityMainBinding
import com.ute.baitaplab5.model.User
import com.ute.baitaplab5.valid.FormValid

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
        setupCancelButton()
    }

    private fun setupClickDebounce() {
        binding.btnSubmit.setOnClickListener {
            val currentTime = SystemClock.elapsedRealtime()
            if (currentTime - lastClickTime < CLICK_DEBOUNCE_DELAY) {
                return@setOnClickListener
            }
            lastClickTime = currentTime

            if (validateForm()) {
                Toast.makeText(this, "Đã tiếp nhận yêu cầu!", Toast.LENGTH_SHORT).show()

                val user = User(
                    name = binding.etFullName.text.toString().trim(),
                    email = binding.etEmail.text.toString().trim(),
                    phone = binding.etPhone.text.toString().trim(),
                    gender = when (binding.rgGender.checkedRadioButtonId) {
                        R.id.rbMale -> "Nam"
                        R.id.rbFemale -> "Nữ"
                        else -> "Khác"
                    }
                )

                val intent = Intent(this, Detail::class.java).apply {
                    putExtra("name", user.name)
                    putExtra("email", user.email)
                    putExtra("phone", user.phone)
                    putExtra("gender", user.gender)
                }
                startActivity(intent)
            } else {
                Snackbar.make(binding.root, "Đăng ký thất bại! Vui lòng kiểm tra lại thông tin.", Snackbar.LENGTH_LONG)
                    .setAction("Đã hiểu") {}
                    .show()
            }
        }
    }

    private fun setupCancelButton() {
        binding.btnCancel.setOnClickListener {
            binding.etFullName.text?.clear()
            binding.etEmail.text?.clear()
            binding.etPassword.text?.clear()
            binding.etPhone.text?.clear()

            binding.tilFullName.error = null
            binding.tilFullName.isErrorEnabled = false
            binding.tilEmail.error = null
            binding.tilEmail.isErrorEnabled = false
            binding.tilPassword.error = null
            binding.tilPassword.isErrorEnabled = false
            binding.tilPhone.error = null
            binding.tilPhone.isErrorEnabled = false

            binding.rgGender.clearCheck()
            binding.cbTerms.isChecked = false
            binding.btnSubmit.isEnabled = false

            binding.etFullName.clearFocus()
            binding.etEmail.clearFocus()
            binding.etPassword.clearFocus()
            binding.etPhone.clearFocus()

            Snackbar.make(binding.root, "Đã làm mới dữ liệu biểu mẫu", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun validateForm(): Boolean {
        var isValid = true

        val name = binding.etFullName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        val phone = binding.etPhone.text.toString().trim()

        if (!FormValid.isValidName(name)) {
            binding.tilFullName.error = "Họ tên không được để trống và tối thiểu 3 ký tự"
            isValid = false
        }

        if (!FormValid.isValidEmail(email)) {
            binding.tilEmail.error = "Email phải đúng định dạng (@ute.vn, @gmail.com,...)"
            isValid = false
        }

        if (!FormValid.isValidPassword(password)) {
            binding.tilPassword.error = "Mật khẩu tối thiểu 6 ký tự, gồm cả chữ và số"
            isValid = false
        }

        if (!FormValid.isValidPhone(phone)) {
            binding.tilPhone.error = "SĐT phải gồm 10 chữ số và bắt đầu bằng 03, 05, 07, 08, 09"
            isValid = false
        }

        return isValid
    }

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

        binding.etPhone.doAfterTextChanged {
            if (binding.tilPhone.error != null) {
                binding.tilPhone.error = null
                binding.tilPhone.isErrorEnabled = false
            }
        }
    }

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