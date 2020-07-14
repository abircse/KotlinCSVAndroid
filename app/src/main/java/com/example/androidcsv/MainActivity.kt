package com.example.androidcsv

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.os.Environment.getExternalStorageDirectory
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {

            exportdata()

        }

    }

    fun exportdata() {

        val stringBuilder = StringBuilder()
        stringBuilder.append("Cadet MobileApp, Report")
        stringBuilder.append("\n" + "Student ID:,--------, Name: Abir")
        stringBuilder.append("\n" + "Name,Age")
        for (i in 1..5) {
            stringBuilder.append("\n" + i.toString() + "," + (i * i))
        }

        try {
            val fileOutputStream = openFileOutput("data.csv", Context.MODE_PRIVATE)
            fileOutputStream.write((stringBuilder.toString()).toByteArray())
            fileOutputStream.close()

            val filelocation = File(filesDir, "data.csv")
            val uri = FileProvider.getUriForFile(
                this,
                "com.example.androidcsv.provider",
                filelocation
            )
            val intent = Intent(Intent.ACTION_SEND)
            intent.setType("text/csv")
            intent.putExtra(Intent.EXTRA_SUBJECT, "Data")
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            startActivity(Intent.createChooser(intent, "Send Mail"))

        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: FileNotFoundException) {
            e.printStackTrace();
        }

    }
}