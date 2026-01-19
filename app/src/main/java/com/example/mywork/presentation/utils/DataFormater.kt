package com.example.mywork.presentation.utils

import android.icu.text.DateFormat
import java.text.SimpleDateFormat

object DataFormater {

    private val formatter = SimpleDateFormat.getDateInstance(DateFormat.SHORT)

    fun formatDateTjString(timestamp: Long): String {
    return  formatter.format(timestamp)
    }
}