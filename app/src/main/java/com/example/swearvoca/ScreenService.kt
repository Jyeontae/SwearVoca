package com.example.swearvoca

import android.app.Service
import android.content.Intent

import android.content.IntentFilter
import android.os.IBinder

class ScreenService : Service(){
    private lateinit var mReceiver : ScreenReceiver

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        mReceiver = ScreenReceiver()
        var filter : IntentFilter = IntentFilter(Intent.ACTION_SCREEN_OFF)
        var quiz : ArrayList<String> = ArrayList()
        var answer : ArrayList<String> = ArrayList()
        registerReceiver(mReceiver, filter)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        if(intent != null){
            if(intent.action == null){
                if(mReceiver == null){
                    mReceiver = ScreenReceiver()
                    var filter : IntentFilter = IntentFilter(Intent.ACTION_SCREEN_OFF)
                    registerReceiver(mReceiver, filter)
                }
            }
        }
        return START_REDELIVER_INTENT
    }

    override fun onDestroy() {
        super.onDestroy()
        if(mReceiver != null){
            unregisterReceiver(mReceiver)
        }
    }
}