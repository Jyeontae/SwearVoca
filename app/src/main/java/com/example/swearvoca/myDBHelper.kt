package com.example.swearvoca

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class myDBHelper(context : Context) : SQLiteOpenHelper(context, "SV_voca", null, 1) {
    override fun onCreate(p0: SQLiteDatabase) {
        p0.execSQL("CREATE TABLE voca(word char(20) PRIMARY KEY, mean char(100));")
    }

    override fun onUpgrade(p0: SQLiteDatabase, p1: Int, p2: Int) {
        p0.execSQL("DROP TABLE IF EXISTS voca")
        onCreate(p0)
    }

}