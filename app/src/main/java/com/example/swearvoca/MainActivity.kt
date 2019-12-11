package com.example.swearvoca

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.Month

class MainActivity : AppCompatActivity() {
    var todayY:Int = 0
    var todayM: Int = 0
    var todayD:Int =0
    var todayHour: Int = 0
    var todayMinute: Int =0
    var wordcount: Int = 30
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle("학습 설정")

        loadTimeData()

        var init_Time : String = "00시 00분"
        gasu.setText("30")
        wordcount  = gasu.text.toString().toInt() //단어개수 디폴트 30
        var total_word: Int = loadData()
        gasu.setText("$wordcount")

        date_btn.setOnClickListener{    //시간 설정
            timePicker.visibility = View.VISIBLE
            tip.visibility = View.VISIBLE
            tip2.visibility = View.INVISIBLE
            gasu.visibility = View.INVISIBLE
        }
        total_btn.setOnClickListener{   //단어 개수 설정
            timePicker.visibility = View.INVISIBLE
            tip.visibility = View.INVISIBLE
            tip2.visibility = View.VISIBLE
            gasu.visibility = View.VISIBLE
        }

        timePicker.setOnTimeChangedListener { timePicker, i, i2 ->
            Toast.makeText(this, "${i}시 ${i2}분으로 설정되었습니다.", Toast.LENGTH_SHORT).show()
            init_Time = "${i}시 ${i2}분"
            todayHour = i
            todayMinute = i2
        } //시간 설정 이벤트

        check.setOnClickListener {
            wordcount = gasu.text.toString().toInt()
            Toast.makeText(this, "시간:${init_Time}, 단어:${wordcount}개 설정되었습니다.", Toast.LENGTH_SHORT).show()
        } // 설정확인 버튼 처리

        start.setOnClickListener {
            todayY = LocalDateTime.now().year
            todayM = LocalDateTime.now().monthValue
            todayD = LocalDateTime.now().dayOfMonth
            saveTimeData(todayY, todayM, todayD, todayHour, todayMinute)
            total_word += wordcount
            saveData(total_word)
            startActivity<study>(
                    "word_count" to wordcount
            )//단어

            finish()
        } //시작 이벤트 처리
    }

    private fun saveData(wordcount : Int){
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = pref.edit()
        editor.putInt("KEY_WORDCOUNT", wordcount)
                .apply()
    }

    private fun loadData(): Int{
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        var wordcount = pref.getInt("KEY_WORDCOUNT",0)
        return wordcount
    }

    private fun saveTimeData(y: Int, m: Int, d: Int, h: Int, m2: Int){
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = pref.edit()
        editor.putInt("KEY_YEAR", y)
            .putInt("KEY_MONTH", m)
            .putInt("KEY_DAY", d)
            .putInt("KEY_HOUR", h)
            .putInt("KEY_MINUTE", m2)
            .apply()
    }

    private fun loadTimeData(){
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
            var s1 =pref.getInt("KEY_YEAR", 2019)
            var s2 = pref.getInt("KEY_MONTH", 1)
            var s3 = pref.getInt("KEY_DAY", 1)
            var s4 = pref.getInt("KEY_HOUR", 0)
            var s5 = pref.getInt("KEY_MINUTE", 0)
            //var dateTime = LocalDateTime.of(s1, s2, s3, s4, s5).plusDays(1)
            var dateTime = LocalDateTime.of(s1, s2, s3, s4, s5) //시간 설정의시간
        Log.d("date", dateTime.toString())
        if(dateTime >= LocalDateTime.now()){
            startActivity<study>(
                "word_count" to wordcount
            )
            finish()
        }
    }
}
