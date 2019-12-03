package com.example.swearvoca

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class ScreenReceiver : BroadcastReceiver(){
    override fun onReceive(p0: Context, p1: Intent) {
        if(p1.action.equals(Intent.ACTION_SCREEN_OFF)){
            val intent : Intent= Intent(p0, toBack::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            p0.startActivities(arrayOf(intent))
        }
    }

}