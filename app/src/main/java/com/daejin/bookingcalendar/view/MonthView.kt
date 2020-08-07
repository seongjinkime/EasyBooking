package com.daejin.bookingcalendar.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.Nullable
import com.daejin.bookingcalendar.interfaces.OnDayClickListener
import com.daejin.bookingcalendar.model.Day
import java.util.*
import kotlin.collections.ArrayList

public class MonthView : LinearLayout, View.OnClickListener {


    private var year: Int = 0
    private var month: Int = 0

    @Nullable
    private var weeks: ArrayList<LinearLayout> = ArrayList(6)
    @Nullable
    private var dayViews: ArrayList<DayView> = ArrayList(42)

    @Nullable
    var onDayClickListener: OnDayClickListener? = null




    constructor(context: Context): super(context) {
        init(context)
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(context)

    }



    private fun init(context: Context) {
        Log.e(javaClass.name, "month view init start")
        orientation = LinearLayout.VERTICAL
        var layout : LinearLayout = LinearLayout(context)

        for (i in 0 until 42) {
            if (i % 7 == 0) {
                layout = LinearLayout(context)
                var params = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0)
                params.weight = 1.0f
                layout.orientation = HORIZONTAL
                layout.layoutParams = params
                layout.weightSum = 7.0f
                weeks.add(layout)
            }

            var params = LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT)
            params.weight = 1.0f
            var dayView = DayView(context)
            dayView.layoutParams = params
            dayView.setOnClickListener(this)

            layout.addView(dayView)
            dayViews.add(dayView)
        }

        if (isInEditMode) {
            val cal = Calendar.getInstance()
            make(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH))
        }
        Log.e(javaClass.name, "month view init end")
    }

    public fun make(year: Int, month: Int) {
        Log.e(javaClass.name, "month view make start")
        if (this.year == year && this.month == month) {
            return
        }
        var makeTime = System.currentTimeMillis()
        this.year = year
        this.month = month

        var cal = Calendar.getInstance()
        cal.set(year, month, 1)
        val monthBeginning = cal.get(Calendar.DAY_OF_WEEK) - 1
        cal.add(Calendar.DAY_OF_MONTH, -monthBeginning)

        val totalDays = monthBeginning + cal.getActualMaximum(Calendar.DAY_OF_MONTH)
        val daysCount = if (totalDays > 35) 42 else 35
        var days: ArrayList<Day> = ArrayList()

        for ( i in 0 until daysCount) {
            days.add(Day(cal.clone() as Calendar))

            cal.add(Calendar.DAY_OF_MONTH, 1)
        }

        if (days.size == 0) {
            return
        }

        removeAllViews()

        for ((idx, day) in days.withIndex()) {
            if (idx % 7 == 0) {
                addView(weeks[idx/7]);
            }

            dayViews[idx].setDay(day)
            if (day.get(Calendar.MONTH) != month) {
                dayViews[idx].setMode(DayView.Mode.HAZY)
            } else {
                dayViews[idx].setMode(DayView.Mode.CLEAR)
            }
            dayViews[idx].refresh()
        }

        weightSum = childCount.toFloat()
        Log.e(javaClass.name, "month view make end")

    }

    override fun shouldDelayChildPressedState(): Boolean {
        return false
    }


    override fun onClick(v: View?) {
        var dayView = v as DayView
        if (onDayClickListener != null) {
            onDayClickListener!!.onClick(dayView)
        }
    }
}
