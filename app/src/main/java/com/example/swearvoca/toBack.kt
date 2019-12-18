package com.example.swearvoca


import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.util.Log.d
import android.view.ContextThemeWrapper
import androidx.appcompat.app.AppCompatActivity
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_toback.*
import kotlinx.coroutines.delay
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.timer
import kotlin.concurrent.timerTask
import kotlin.random.Random

/** 잠금화면 레이아웃 **/
class toBack : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toback)
        setTitle("QUIZ")

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )//레이아웃을 풀스크린으로

        window.addFlags(
            WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
                    WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
        )//잠금화면일때 현재 레이아웃 보이기


        var quiz : ArrayList<String> = ArrayList()//단어
        var answer : ArrayList<String> = ArrayList()//뜻
        var check : ArrayList<Int> = ArrayList()//난수저장

        val myHelper = myDBHelper(this)
        var sqlDB : SQLiteDatabase = myHelper.readableDatabase
        var cursor : Cursor

        cursor = sqlDB.rawQuery("SELECT * FROM voca;", null)

        var s1 : String = ""
        var s2 : String = ""
        while(cursor.moveToNext()){
            s1 = cursor.getString(0)
            s2 = cursor.getString(1)
            quiz.add(s1)
            answer.add(s2)
        }//db내용을 ArrayList에 가져옴

        val random = Random
        for(i in 0..4) {
            val num = random.nextInt(30)
            if(!check.contains(num)){
                check.add(num)
                Log.d("123", num.toString())
            }
        }//check리스트에 0~4까지의 중복없이 난수를 출력

    Log.d("quiz_size", quiz.size.toString())
        val test_word = random.nextInt(4)
        word.setText("${quiz[check[test_word]]}") //퀴즈 단어
        take1.setText("${answer[check[0]]}") //보기1
        take2.setText("${answer[check[1]]}")//보기2
        take3.setText("${answer[check[2]]}")//보기3
        take4.setText("${answer[check[3]]}")//보기4

        take1.setOnClickListener {
            if(check[test_word] == check[0]) {//정답일때
                finish()
            }
            else{
                failalert(quiz[check[test_word]], answer[check[test_word]])//오답일때
            }
        }
        take2.setOnClickListener {
            if(check[test_word] == check[1]) {
                finish()
            }
            else{
                failalert(quiz[check[test_word]], answer[check[test_word]])
            }
        }
        take3.setOnClickListener {
            if(check[test_word] == check[2]) {
                finish()
            }
            else{
                failalert(quiz[check[test_word]], answer[check[test_word]])
            }
        }
        take4.setOnClickListener {
            if(check[test_word] == check[3]) {
                finish()
            }
            else{
                failalert(quiz[check[test_word]], answer[check[test_word]])
            }
        }
        var count =0
        count = loadCount()
        fail.setOnClickListener {//정말 모르겠습니다 버튼을 클릭했을 시
            if(count == 5){
                fail.isEnabled = false
            }
            else{
                count++
                saveCount(count)
                finish()
            }
            fail.setText("정말 모르겠습니다(${count} / 5)")
        }

        cursor = sqlDB.rawQuery("DELETE FROM voca;", null) //db data 삭제
        cursor.close()
        sqlDB.close()


    }
    private fun failalert(voca: String, mean: String){ //틀렸을 때 기능을 수행
        val builder = AlertDialog.Builder(ContextThemeWrapper(this, R.style.Theme_AppCompat_Light_Dialog))
        builder.setTitle("틀림!")
        builder.setMessage("${voca}의 뜻은 ${mean}입니다. \n틀렸으므로 5분동안 잠깁니다.")
        builder.setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
            pauseNstart()
        }
        builder.show()
    }

    private fun pauseNstart(){ // 화면이 잠기는 기능
        window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)//화면 터치불가로 만듦
        Thread.sleep(5000L)//5초
        //Thread.sleep(300000L)//5분
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)//화면 터치 가능으로 전환
        finish() //액티비티 종료
    }

    fun saveCount(count : Int){ //정말모르겠습니다버튼 count
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = pref.edit()
        editor.putInt("COUNT", count)
            .apply()
    }

    private fun loadCount(): Int{ //단어 초기화 시간과 현재시간을 비교
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        var s1 =pref.getInt("KEY_YEAR", 2019)
        var s2 = pref.getInt("KEY_MONTH", 1)
        var s3 = pref.getInt("KEY_DAY", 1)
        var s4 = pref.getInt("KEY_HOUR", 0)
        var s5 = pref.getInt("KEY_MINUTE", 0)

        var count = pref.getInt("COUNT",0)
        if(LocalDateTime.now() >= LocalDateTime.of(s1, s2, s3, s4, s5)){
            count = 0
        }
        return count
    }

}
