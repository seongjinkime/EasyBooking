package com.daejin.bookingcalendar.model

import java.util.*

public class Day {
    private var date = Calendar.getInstance()

    constructor(date: Calendar) {
        setDate(date)
    }


    public fun setDate(date: Calendar) {
        this.date = date
    }

    public fun setDate(year: Int, mon: Int, day: Int) {
        this.date.set(year, mon, day)
    }

    public fun getDate() : Calendar = this.date

    public fun get(field :Int) : Int = this.date.get(field)







}