package com.example.swearvoca

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import java.util.*
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {
    var date_count : Int = 0
    val tz = TimeZone.getTimeZone("Asia/Seoul")
    val gc = GregorianCalendar(tz)
    val sh = gc.get(GregorianCalendar.HOUR).toString()
    val sm = gc.get(GregorianCalendar.MINUTE).toString()
    var f: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle("학습 설정")
        Log.d("asd", "$sh, $sm")
        var init_Time : String = "00시 00분"
        var wordcount : Int = gasu.text.toString().toInt()
        var hour : Int = 0
        var minute : Int = 0
        loadData()
        gasu.setText("$wordcount")


        date_btn.setOnClickListener{
            timePicker.visibility = View.VISIBLE
            tip.visibility = View.VISIBLE
            tip2.visibility = View.INVISIBLE
            gasu.visibility = View.INVISIBLE
        }
        total_btn.setOnClickListener{
            timePicker.visibility = View.INVISIBLE
            tip.visibility = View.INVISIBLE
            tip2.visibility = View.VISIBLE
            gasu.visibility = View.VISIBLE
        }

        timePicker.setOnTimeChangedListener { timePicker, i, i2 ->
            Toast.makeText(this, "${i}시 ${i2}분으로 설정되었습니다.", Toast.LENGTH_SHORT).show()
            init_Time = "${i}시 ${i2}분"
            hour  = i
            minute = i2
        }

        check.setOnClickListener {
            wordcount = gasu.text.toString().toInt()
            Toast.makeText(this, "시간:${init_Time}, 단어:${wordcount}개 설정되었습니다.", Toast.LENGTH_SHORT).show()
            //saveData(wordcount, init_Time)
        }
        start.setOnClickListener {
            saveData(wordcount, hour, minute)
            startActivity<study>(
                    "word_count" to wordcount
            )
            finish()
        }

    }
    private fun saveData(wordcount : Int, hour : Int, minute : Int){
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = pref.edit()
        editor.putInt("KEY_WORDCOUNT", wordcount)
                .putString("KEY_HOUR", hour.toString())
            .putString("KEY_MINUTE", minute.toString())
                .apply()
    }

    private fun loadData(){
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        var wordcount = pref.getInt("KEY_WORDCOUNT",0)
        var hour = pref.getString("KEY_HOUR","0")
        var minute = pref.getString("KEY_MINUTE", "0")
    }
}
