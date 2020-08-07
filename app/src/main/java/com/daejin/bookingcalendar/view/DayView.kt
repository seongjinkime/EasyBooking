package com.daejin.bookingcalendar.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.core.content.res.ResourcesCompat
import com.daejin.easybooking.R
import com.daejin.bookingcalendar.model.Day
import java.util.*

public class DayView : RelativeLayout {

    enum class Mode {
        CLEAR,
        HAZY
    }

    @NonNull
    private lateinit var day: Day
    private lateinit var view: View
    private lateinit var dayTv: TextView

    private var mode: Mode =
        Mode.CLEAR

    constructor(@NonNull context: Context) : super(context) {
        init(context)
    }
    constructor(@NonNull context: Context, @Nullable attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    fun init(context: Context) {
        view = View.inflate(context, R.layout.day_view, this)
        dayTv = view.findViewById<TextView>(R.id.tv_date) as TextView
    }

    public fun setMode(mode: Mode) {
        this.mode = mode
    }

    public fun setDay(year: Int, month: Int, date: Int) {
        this.day.setDate(year, month, date)
    }

    public fun setDay(calendar: Calendar){
        this.day.setDate(calendar)
    }

    public fun setDay(day : Day) {
        this.day = day
    }

    public fun getDay() : Day = this.day

    public fun get(field : Int): Int {
        return this.day.get(field)
    }


    public fun refresh() {
        dayTv.text = day.get(Calendar.DATE).toString()
        setDayColor()

    }

    private fun setDayColor() {
        val black = if (mode == Mode.CLEAR) R.color.clear_black else R.color.hazy_black
        val red = if (mode == Mode.CLEAR) R.color.clear_red else R.color.hazy_red
        val blue = if (mode == Mode.CLEAR) R.color.clear_blue else R.color.hazy_blue

        when(day.get(Calendar.DAY_OF_WEEK)) {
            Calendar.SUNDAY -> dayTv.setTextColor(ResourcesCompat.getColor(context.resources, red, null))
            Calendar.SATURDAY -> dayTv.setTextColor(ResourcesCompat.getColor(context.resources, blue, null))
            else -> dayTv.setTextColor(ResourcesCompat.getColor(context.resources, black, null))
        }
    }

}