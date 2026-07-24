package com.yoesuv.kmpformvalidation.utils.validation

/**
 * Compiled once at class load; reused across every invocation
 * to avoid recompiling the pattern on each keystroke.
 */
private val EMAIL_REGEX = Regex(
    pattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
)

/**
 * Helper function to check email format using regex
 * @return true if email format is valid, false otherwise
 */
fun String.isValidEmailFormat(): Boolean = EMAIL_REGEX.matches(this)