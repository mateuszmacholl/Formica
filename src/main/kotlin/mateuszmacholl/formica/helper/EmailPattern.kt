package mateuszmacholl.formica.helper

import java.util.regex.Pattern

class EmailPattern {
    companion object {
        private var VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)

        fun isCorrect(value: String): Boolean {
            return VALID_EMAIL_ADDRESS_REGEX.matcher(value).matches()
        }
    }
}