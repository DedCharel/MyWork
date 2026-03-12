package com.example.mywork.presentation.utils

import android.icu.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Locale

object DataFormater {

    private val formatter = SimpleDateFormat.getDateInstance(DateFormat.SHORT)

    fun formatDateToString(timestamp: Long): String {
    return   SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        .format(java.util.Date(timestamp))
    }
    fun getMonthToString(timestamp: Long): String {
        return   SimpleDateFormat("MMM", Locale.getDefault())
            .format(java.util.Date(timestamp))
    }
    fun getDayToString(timestamp: Long): String {
        return   SimpleDateFormat("dd", Locale.getDefault())
            .format(java.util.Date(timestamp))
    }
    fun getYearToString(timestamp: Long): String {
        return   SimpleDateFormat("yyyy", Locale.getDefault())
            .format(java.util.Date(timestamp))
    }
}