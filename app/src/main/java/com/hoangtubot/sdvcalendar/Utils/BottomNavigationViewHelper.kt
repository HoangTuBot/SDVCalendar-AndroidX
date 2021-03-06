package com.hoangtubot.sdvcalendar.Utils

import android.content.Context
import android.content.Intent
import android.support.design.widget.BottomNavigationView
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
import com.google.android.gms.ads.InterstitialAd
import com.hoangtubot.sdvcalendar.*

/**
 * Created by hoang on 1/17/2018.
 */

object BottomNavigationViewHelper {
    /*
    BottomNavigationView setup
    */
    fun setupBottomNavigationView(bottomNavigationViewEx: BottomNavigationViewEx) {
        bottomNavigationViewEx.enableAnimation(false)
        bottomNavigationViewEx.enableItemShiftingMode(false)
        bottomNavigationViewEx.enableShiftingMode(false)
    }
    fun enableNavigation(context: Context, view: BottomNavigationView, page: Int,mInterstitialAd: InterstitialAd) {
        view.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_bus -> {
                    if (page != 1) {
                        val intent1 = Intent(context, MainActivity::class.java) //ACTIVITY_NUM = 0
                        intent1.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                        context.startActivity(intent1)
                        if (mInterstitialAd.isLoaded()) {
                            mInterstitialAd.show()
                        } else {
                        }
                    }
                    else println("You're at this page")
                }
                R.id.menu_a -> {
                    if (page != 2){
                        val intent2 = Intent(context, KipAactivity::class.java)//ACTIVITY_NUM = 1
                        intent2.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                        context.startActivity(intent2)
                        if (mInterstitialAd.isLoaded()) {
                            mInterstitialAd.show()
                        } else {
                        }
                    }
                    else println("You're at this page")
                }
                R.id.menu_b -> {
                    if (page != 3) {
                        val intent3 = Intent(context, KipBactivity::class.java)//ACTIVITY_NUM = 2
                        intent3.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                        context.startActivity(intent3)
                        if (mInterstitialAd.isLoaded()) {
                            mInterstitialAd.show()
                        } else {
                        }
                    }
                    else println("You're at this page")
                }
                R.id.menu_c -> {
                    if (page != 4) {
                        val intent4 = Intent(context, KipCactivity::class.java)//ACTIVITY_NUM = 4
                        intent4.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                        context.startActivity(intent4)
                        if (mInterstitialAd.isLoaded()) {
                            mInterstitialAd.show()
                        } else {
                        }
                    }
                    else println("You're at this page")
                }
            }
            false
        }
    }
}
