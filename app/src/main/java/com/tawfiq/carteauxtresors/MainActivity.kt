package com.tawfiq.carteauxtresors

import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileWriter
import java.io.IOException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val carte = Carte(this,5,4)
        carte.insertTresor(2,2,3)
        carte.insertTresor(1,2,5)
        carte.insertMontagne(0,2)
        carte.departAventurier(2,1,"D","A-A-G-A")

        fileDepart(carte)
    }

    private fun fileDepart (carte: Carte){
        try {
            val root = File(Environment.getExternalStorageDirectory(),"Documents")
            if (!root.exists()) root.mkdirs()

            val file = File(root, "carte.txt")
            val writer = FileWriter(file)

            carte.showCarte(writer)

            writer.flush()
            writer.close()
            Toast.makeText(baseContext, "Saved", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}