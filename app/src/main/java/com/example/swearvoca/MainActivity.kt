package com.example.swearvoca

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle("학습 설정")

        var init_Time : String = "00시 00분"
        gasu.setText("30")
        var wordcount : Int = gasu.text.toString().toInt() //단어개수 디폴트 30
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
        } //시간 설정 이벤트

        check.setOnClickListener {
            wordcount = gasu.text.toString().toInt()
            Toast.makeText(this, "시간:${init_Time}, 단어:${wordcount}개 설정되었습니다.", Toast.LENGTH_SHORT).show()
        } // 설정확인 버튼 처리

        start.setOnClickListener {
            total_word += wordcount
            saveData(total_word)
            startActivity<study>(
                    "word_count" to wordcount
            )
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
}
