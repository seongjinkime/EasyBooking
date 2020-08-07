package com.daejin.bookingcalendar.interfaces

import androidx.annotation.NonNull
import com.daejin.bookingcalendar.view.DayView

public interface CalendarView {
    fun onDayClick(@NonNull odv: DayView)
    fun onMonthChange(year: Int, month: Int)
    fun getCurrentPosition() : Int
}