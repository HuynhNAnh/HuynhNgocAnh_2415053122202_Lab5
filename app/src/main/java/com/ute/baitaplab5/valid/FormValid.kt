package com.ute.baitaplab5.valid

object FormValid {
    private val EMAIL_REGEX =
        Regex("^[A-Za-z0-9._%+-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,6}|ute\\.vn|gmail\\.com)$")

    private val PASSWORD_REGEX =
        Regex("^(?=.*[A-Za-z])(?=.*\\d).{6,}$")

    private val PHONE_REGEX =
        Regex("^(03|05|07|08|09)\\d{8}$")

    fun isValidName(name: String): Boolean =
        name.trim().isNotEmpty() && name.trim().length >= 3

    fun isValidEmail(email: String): Boolean =
        email.isNotBlank() && EMAIL_REGEX.matches(email.trim())

    fun isValidPassword(password: String): Boolean =
        PASSWORD_REGEX.matches(password)

    fun isValidPhone(phone: String): Boolean =
        PHONE_REGEX.matches(phone.trim())
}