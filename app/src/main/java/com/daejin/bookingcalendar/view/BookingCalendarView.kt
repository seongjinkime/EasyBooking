package com.daejin.bookingcalendar.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.Nullable
import androidx.viewpager.widget.ViewPager
import com.daejin.easybooking.R
import com.daejin.bookingcalendar.interfaces.CalendarEventListener
import com.daejin.bookingcalendar.interfaces.CalendarView
import java.util.*

class BookingCalendarView : LinearLayout,
    CalendarView {
    private var year = 0
    private var month = 0

    private lateinit var pager: ViewPager
    private lateinit var adapter: MonthlyPagerAdapter

    @Nullable
    private var calendarEventListener: CalendarEventListener? = null

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
        var inflater: LayoutInflater = LayoutInflater.from(context)
        var v = inflater.inflate(R.layout.booking_calendar_view, this)

        val cal = Calendar.getInstance()
        year = cal.get(Calendar.YEAR)
        month = cal.get(Calendar.MONTH)

        pager = v.findViewById(R.id.monthPager) as ViewPager
        Log.e(javaClass.name, "start pager adapter")
        adapter =
            MonthlyPagerAdapter(context, this)


        pager.adapter = adapter
        pager.setOnPageChangeListener(adapter)
        pager.setCurrentItem(adapter.getPosition(year, month))
        pager.offscreenPageLimit = 1

        Log.e(javaClass.name, "end pager adapter")
    }

    fun setCalendarEventListener(@Nullable calendarEventListener: CalendarEventListener?){
        this.calendarEventListener = calendarEventListener
    }

    override fun onDayClick(dayView: DayView) {
        if (calendarEventListener != null) {
            calendarEventListener!!.onDayClick(dayView)
        }
    }

    override fun onMonthChange(year: Int, month: Int) {
        if (calendarEventListener != null) {
            calendarEventListener!!.onChange(year, month)
        }
    }

    override fun getCurrentPosition(): Int {
        return pager.currentItem
    }

}