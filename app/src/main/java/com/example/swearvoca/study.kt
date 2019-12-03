package com.example.swearvoca


import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.opencsv.CSVReader
import kotlinx.android.synthetic.main.activity_study.*
import org.jetbrains.anko.startActivity
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.random.Random


class study : AppCompatActivity() {
    internal var word : ArrayList<String> = ArrayList()
    internal var mean : ArrayList<String> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_study)
        setTitle("영단어 학습하기")

        val myHelper = myDBHelper(this)
        var sqlDB : SQLiteDatabase = myHelper.writableDatabase
        myHelper.onUpgrade(sqlDB, 1, 2)

        var word_count : Int = intent.getIntExtra("word_count", 0)
        var total_count : Int = 0
        
        try {
            val iss: InputStreamReader = InputStreamReader(resources.openRawResource(R.raw.voca), "UTF-8")
            val reader: BufferedReader = BufferedReader(iss)
            val read : CSVReader = CSVReader(reader)
            var record : List<String>
            total_count = total_count + word_count //30
            var i : Int = 0
            val flag : Int = total_count - word_count
            do{
                if(i >= flag) {
                    record = read.readNext().toList()
                    word.add(record.toString().split(",")[0].replace("[", ""))
                    mean.add(record.toString().split(",")[1].replace("]", ""))
                }
                i++
            }while(i<total_count)
        }catch (e : Exception){
            e.printStackTrace()
        }

        var s : Int = 0
        wordView.text = word[s]
        meanView.text = mean[s]
        countView.text = "${s+1} / ${word.size}"

        next.setOnClickListener{
            if(s+1 == word.size){
                s = -1
            }
            s++
            wordView.text = word[s]
            meanView.text = mean[s]
            countView.text = "${s+1} / ${word.size}"
        }

        prev.setOnClickListener {
            if(s == 0){
                s = 30
            }
            s--
            wordView.text = word[s]
            meanView.text = mean[s]
            countView.text = "${s+1} / ${word.size}"
        }

        swear.setOnClickListener {
            var random = Random
            sqlDB = myHelper.writableDatabase
            Log.d("123", word.size.toString())

            var i : Int =0
            while (i<word.size) {
                sqlDB.execSQL("INSERT INTO voca VALUES('"
                        + word[i]+"','"
                        +mean[i]+"');")
                i++
            }
            sqlDB.close()
            val intent = Intent(applicationContext, ScreenService::class.java)
            startService(intent)
            finish()
        }
    }

}
