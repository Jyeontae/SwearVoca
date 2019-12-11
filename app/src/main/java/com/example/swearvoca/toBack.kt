package com.example.swearvoca


import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
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
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.timer
import kotlin.concurrent.timerTask
import kotlin.random.Random


class toBack : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toback)
        setTitle("QUIZ")

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        window.addFlags(
            WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
                    WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
        )


        var quiz : ArrayList<String> = ArrayList()
        var answer : ArrayList<String> = ArrayList()
        var check : ArrayList<Int> = ArrayList()

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
        }

        val random = Random
        for(i in 0..4) {
            val num = random.nextInt(30)
            if(!check.contains(num)){
                check.add(num)
                Log.d("123", num.toString())
            }
        }

    Log.d("quiz_size", quiz.size.toString())
        val test_word = random.nextInt(4)
        word.setText("${quiz[check[test_word]]}")
        take1.setText("${answer[check[0]]}")
        take2.setText("${answer[check[1]]}")
        take3.setText("${answer[check[2]]}")
        take4.setText("${answer[check[3]]}")
        take1.setOnClickListener {
            if(check[test_word] == check[0]) {
                finish()
            }
            else{
                failalert(quiz[check[test_word]], answer[check[test_word]])
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
        var count : Int = 0
        fail.setOnClickListener {
            if(count == 5){
                fail.isEnabled = false
            }
            else{
                count++
                finish()
            }
            fail.setText("정말 모르겠습니다(${count} / 5)")
        }

        cursor = sqlDB.rawQuery("DELETE FROM voca;", null)
        cursor.close()
        sqlDB.close()


    }
    private fun failalert(voca: String, mean: String){
        val builder = AlertDialog.Builder(ContextThemeWrapper(this, R.style.Theme_AppCompat_Light_Dialog))
        builder.setTitle("틀림!")
        builder.setMessage("${voca}의 뜻은 ${mean}입니다. \n틀렸으므로 5분동안 잠깁니다.")
        builder.setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
            pauseNstart()
        }
        builder.show()
    }

    private fun pauseNstart(){ // 5분 터치 x 후 터치가능
        window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        Thread.sleep(5000L)
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        finish()
    }



}
