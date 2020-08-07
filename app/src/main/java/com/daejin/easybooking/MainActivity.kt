package com.daejin.easybooking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.daejin.bookingcalendar.view.BookingCalendarView
import com.daejin.bookingcalendar.view.DayView
import com.daejin.bookingcalendar.interfaces.CalendarEventListener
import java.util.*

class MainActivity: AppCompatActivity() {
    val TAG = "MAIN ACTIVITY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val calendarView = findViewById<BookingCalendarView>(R.id.book_calendar_view)
        calendarView.setCalendarEventListener(object :
            CalendarEventListener {
            override fun onDayClick(dayView: DayView) {
                Log.e(TAG, dayView.get(Calendar.DATE).toString())
            }

            override fun onChange(year: Int, month: Int) {

                Toast.makeText(this@MainActivity, year.toString() + " " + month.toString(), Toast.LENGTH_SHORT).show()
                Log.e(TAG, year.toString() + " " + month.toString())
            }
        })
        Toast.makeText(this@MainActivity, "start", Toast.LENGTH_SHORT).show()

//        val mf = supportFragmentManager.findFragmentById(R.id.monthly) as MonthlyFragment
//
//        mf.setMonthlyFragmentListener(object : MonthlyFragmentListener{
//            override fun onDayClick(dayView: DayView) {
//                Log.e(TAG, dayView.get(Calendar.DATE).toString())
//            }
//
//            override fun onChange(year: Int, month: Int) {
//
//                Toast.makeText(this@MainActivity, year.toString() + " " + month.toString(), Toast.LENGTH_SHORT).show()
//                Log.e(TAG, year.toString() + " " + month.toString())
//            }
//        })
    }
}
