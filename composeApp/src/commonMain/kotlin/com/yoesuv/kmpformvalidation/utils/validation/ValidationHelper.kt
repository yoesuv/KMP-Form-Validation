package com.yoesuv.kmpformvalidation.utils.validation

/**
 * Helper function to check email format using regex
 * @return true if email format is valid, false otherwise
 */
fun String.isValidEmailFormat(): Boolean {
    val emailRegex = Regex(
        pattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    )
    return emailRegex.matches(this)
}