package com.daejin.bookingcalendar.interfaces

import com.daejin.bookingcalendar.view.DayView

public interface CalendarEventListener {
    fun onChange(year: Int, month: Int)
    fun onDayClick(dayView: DayView)
}