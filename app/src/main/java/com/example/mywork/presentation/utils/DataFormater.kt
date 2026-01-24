package com.example.mywork.presentation.utils

import android.icu.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Locale

object DataFormater {

    private val formatter = SimpleDateFormat.getDateInstance(DateFormat.SHORT)

    fun formatDateToString(timestamp: Long): String {
    return   SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        .format(java.util.Date(timestamp))
    }
}