package com.daejin.bookingcalendar.view

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.daejin.bookingcalendar.model.YearMonth
import com.daejin.bookingcalendar.interfaces.CalendarView
import com.daejin.bookingcalendar.interfaces.OnDayClickListener
import java.util.*
import kotlin.collections.ArrayList

class MonthlyPagerAdapter : PagerAdapter, ViewPager.OnPageChangeListener,
    OnDayClickListener {
    private lateinit var calendarView: CalendarView
    private val BASE_YEAR = 2020
    private val BASE_MONTH = Calendar.JANUARY
    private lateinit var BASE_CAL : Calendar

    private val PAGES = 5
    private val TOTAL_PAGES = 1000
    private val BASE_POSITION = TOTAL_PAGES / 2
    private var prevPos = 0

    private var monthViews: ArrayList<MonthView> = ArrayList()

    constructor(context: Context, calendarView: CalendarView) {
        this.calendarView = calendarView
        var base = Calendar.getInstance()
        base.set(BASE_YEAR, BASE_MONTH, 1)
        BASE_CAL = base


        for (i in 0 until PAGES) {
            monthViews.add(MonthView(context))
            Log.e(javaClass.name, "month view add")
        }
    }

    public fun getYearMonth(pos: Int): YearMonth {
        var cal: Calendar = BASE_CAL.clone() as Calendar
        cal.add(Calendar.MONTH, pos - BASE_POSITION)
        return YearMonth(
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH)
        )
    }

    private fun howFarFromBase(year: Int, month: Int) : Int{
        val distY = (year - BASE_YEAR) * 12
        val distM = month - BASE_MONTH
        return distY + distM
    }

    fun getPosition(year: Int, month: Int) : Int {
        var cal = Calendar.getInstance()
        cal.set(year, month, 1)
        return BASE_POSITION + howFarFromBase(year, month)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        Log.e(javaClass.name, "instantiateItem start")
        val dist = position - BASE_POSITION
        var cal = BASE_CAL.clone() as Calendar
        cal.add(Calendar.MONTH, dist)
        val pos = position % PAGES
        container.addView(monthViews[pos])

        monthViews[pos].make(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH))
        monthViews[pos].onDayClickListener = this
        Log.e(javaClass.name, "instantiateItem end")
        return monthViews[pos]
    }

    override fun getCount(): Int {
        return TOTAL_PAGES
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        (`object` as MonthView).onDayClickListener = null
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun onPageScrollStateChanged(state: Int) {
        if (state == ViewPager.SCROLL_STATE_DRAGGING) {
            prevPos = if (calendarView != null) calendarView.getCurrentPosition() else 0
        }
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        if (prevPos != position) {
            prevPos = position
            var ym = getYearMonth(position)
            if (calendarView != null) {
                calendarView.onMonthChange(ym.year, ym.moth + 1)
            }
        }
    }

    override fun onPageSelected(position: Int) {}

    override fun onClick(dayView: DayView) {
        if (calendarView != null) {
            calendarView.onDayClick(dayView)
        }
    }






}