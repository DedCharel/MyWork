package com.example.mywork.presentation.utils

import java.time.LocalDate
import java.time.ZoneId

object DateUtils {
    fun  getStartOfCurrentMonth() = LocalDate.now()
        .withDayOfMonth(1) // Устанавливаем 1 число
        .atStartOfDay()    // Полночь (00:00:00)
        .atZone(ZoneId.systemDefault()) // Учитываем временную зону
        .toInstant()
        .toEpochMilli()

}