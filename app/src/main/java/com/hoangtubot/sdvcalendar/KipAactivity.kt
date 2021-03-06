package com.hoangtubot.sdvcalendar

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.gms.ads.*

import com.hoangtubot.sdvcalendar.Utils.BottomNavigationViewHelper
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.hoangtubot.sdvcalendar.Utils.CalendarViewHelper
import android.os.AsyncTask
import android.view.View
import com.hoangtubot.sdvcalendar.decorators.*
import org.threeten.bp.LocalDate
import java.util.*
import java.util.concurrent.Executors

class KipAactivity:AppCompatActivity()
{
    private lateinit var mAdView : AdView
    lateinit var bottomNavViewBar: BottomNavigationViewEx
    var ACTIVITY_NUM: Int = 1
    lateinit var calendarViewKipA: MaterialCalendarView
    private val oneDayDecorator = OneDayDecorator()
    private lateinit var mInterstitialAd: InterstitialAd

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kipa)
        setupInterstitialAd()
        setupBottomNavigationView()
        setupAdView()
        setupCalendarView()
        KipAday().executeOnExecutor(Executors.newSingleThreadExecutor())
        //KipAnight().executeOnExecutor(Executors.newSingleThreadExecutor())
        //PublicHolidays().executeOnExecutor(Executors.newSingleThreadExecutor())
        //SamsungHolidays().executeOnExecutor(Executors.newSingleThreadExecutor())
    }

    private fun setupInterstitialAd()
    {
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = "ca-app-pub-6463279426967492/6473919021"
        mInterstitialAd.loadAd(AdRequest.Builder().build())
        mInterstitialAd.adListener = object : AdListener()
        {
            override fun onAdClosed()
            {
                mInterstitialAd.loadAd(AdRequest.Builder().build())
            }
            override fun onAdLoaded()
            {
                toast("Ad Loaded")
            }
        }
    }

    private fun toast (message: String, tag: String = KipAactivity::class.java.simpleName,length: Int=Toast.LENGTH_SHORT)
    {
        Toast.makeText(this,"[$tag] $message",length).show()
    }
    private fun setupBottomNavigationView()
    {
        bottomNavViewBar = findViewById(R.id.bottomNavViewBar)
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavViewBar)
        BottomNavigationViewHelper.enableNavigation(this,bottomNavViewBar,2, mInterstitialAd)
        val menu: Menu = bottomNavViewBar.getMenu()
        val menuItem: MenuItem = menu.getItem(ACTIVITY_NUM)
        menuItem.setChecked(true)
    }
    private fun setupAdView()
    {
        mAdView = findViewById(R.id.adViewA)
        MobileAds.initialize(this, "ca-app-pub-6463279426967492~1968408247");
        val adView = AdView(this)
        adView.adSize = AdSize.BANNER
        adView.adUnitId = "ca-app-pub-6463279426967492/5548506700"
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }
    private fun setupCalendarView()
    {
        calendarViewKipA = findViewById(R.id.calendarViewKipA)
        calendarViewKipA.setOnDateChangedListener({widget, date, selected ->  onDateListener(widget,date,selected)})
        calendarViewKipA.setOnTitleClickListener(View.OnClickListener {
            oneDayDecorator.setDate(CalendarDay.today().date)
            calendarViewKipA.setSelectedDate(CalendarDay.today())
            calendarViewKipA.invalidateDecorators()
            calendarViewKipA.setCurrentDate(CalendarDay.today().date)
        })

        CalendarViewHelper.setupCalendarView(calendarViewKipA)
        calendarViewKipA.addDecorators(
                /*HighlightWeekendsDecorator(),
                PublicHolidaysDecorator(),
                SamsungHolidaysDecorator(),*/
                MySelectorDecorator(this),
                oneDayDecorator
        )
    }
    private fun onDateListener(widget: MaterialCalendarView, date: CalendarDay, selected: Boolean)
    {
        //If you change a decorate, you need to invalidate decorators
        oneDayDecorator.setDate(date.date)
        widget.invalidateDecorators()
    }
    override fun onBackPressed()
    {
        super.onBackPressed()
        val intent1 = Intent(this, MainActivity::class.java) //ACTIVITY_NUM = 0
        this.startActivity(intent1)
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show()
        } else {
        }
    }

    private inner class KipAday : AsyncTask<Void, Void, List<CalendarDay>>()
    {
        override fun doInBackground(vararg voids: Void): List<CalendarDay>
        {
            try
            {
                Thread.sleep(2000)
            }
            catch (e: InterruptedException)
            {
                e.printStackTrace()
            }

            var calendar: LocalDate
            val kipAFirstWorkingDay = Workday(CalendarDay.from(2019, 1, 2), 2)

            var kipAWorkDays: MutableList<Workday> = mutableListOf()
            kipAWorkDays.add(kipAFirstWorkingDay)
            //Khoang cach giua cac ngay di lam
            val workdaybetweenlistA = longArrayOf(10, 12, 12, 17, 12, 12, 12, 12, 12, 15, 12, 12, 12, 12, 12, 12, 12, 12, 12, 13, 12, 12, 12, 12, 12, 12, 12, 12, 12)
            //Tong so ngay di lam 1 kip
            val workdaybetweenlistAlong = intArrayOf(4, 4, 4, 4, 4, 4, 4, 4, 5, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4)
            calendar = kipAFirstWorkingDay.calendarDay.date
            for (i in workdaybetweenlistA.indices)
            {
                calendar = calendar.plusDays(workdaybetweenlistA[i])
                 //calendar = calendar.plusDays(10)

                val kipAWorkday = Workday(CalendarDay.from(calendar), workdaybetweenlistAlong[i])
                kipAWorkDays.add(kipAWorkday)
            }
            var dates: MutableList<CalendarDay> = mutableListOf()
            for (item in kipAWorkDays)
            {
                var day = item.calendarDay
                calendar = day.date
                for (j in 0 until item.daylong)
                {
                    day = CalendarDay.from(calendar)
                    dates.add(day)
                    calendar = calendar.plusDays(1)
                }
            }
            return dates
        }

        override fun onPostExecute(calendarDays: List<CalendarDay>)
        {
            super.onPostExecute(calendarDays)
            if (isFinishing)
            {
                return
            }
            calendarViewKipA.addDecorator(EventDecorator(Color.parseColor("#FF00B7F4"), calendarDays))
        }
    }
/*
    private inner class KipAnight : AsyncTask<Void, Void, List<CalendarDay>>() {
        override fun doInBackground(vararg voids: Void): List<CalendarDay> {
            try {
                Thread.sleep(2000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            val calendar = Calendar.getInstance()
            val worknightsKipA: MutableList<Workday> = mutableListOf()
            val kipAWorkfirstnight = Workday(CalendarDay.from(2018, 0, 6), 4)
            worknightsKipA.add(kipAWorkfirstnight)
            val worknightbetweenlistA = intArrayOf(12, 12, 12, 17, 12, 12, 12, 12, 15, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 13, 12, 12, 12, 12, 12, 12, 12, 12, 12)
            val worknightbetweenlistAlong = intArrayOf(4, 4, 9, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 5, 4, 4, 4, 4, 4, 4, 4, 4, 4, 3)
            kipAWorkfirstnight.calendarDay.copyTo(calendar)

            for (i in worknightbetweenlistA.indices) {
                calendar.add(Calendar.DATE, worknightbetweenlistA[i])
                val kipAWorknight = Workday(CalendarDay.from(calendar), worknightbetweenlistAlong[i])
                worknightsKipA.add(kipAWorknight)
            }

            val dates: MutableList<CalendarDay> = mutableListOf()
            for (item in worknightsKipA) {
                var day = item.calendarDay
                day.copyTo(calendar)
                for (j in 0 until item.daylong) {
                    day = CalendarDay.from(calendar)
                    dates.add(day)
                    calendar.add(Calendar.DATE, 1)
                }
            }
            return dates
        }

        override fun onPostExecute(calendarDays: List<CalendarDay>) {
            super.onPostExecute(calendarDays)

            if (isFinishing) {
                return
            }
            calendarViewKipA.addDecorator(EventDecorator(Color.DKGRAY, calendarDays))
        }
    }
*/
}