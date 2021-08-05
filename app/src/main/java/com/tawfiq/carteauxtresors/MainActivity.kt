package com.tawfiq.carteauxtresors

import android.os.Bundle
import android.os.Environment
import android.view.View
import android.view.View.OnClickListener
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tawfiq.carteauxtresors.interfaces.toast
import java.io.File
import java.io.FileNotFoundException
import java.io.FileWriter
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity(),OnClickListener,AdapterView.OnItemSelectedListener {

    private lateinit var carte : Carte
    private lateinit var buttonArriver :Button
    private lateinit var buttonDepart :Button
    private lateinit var imageButtonAvancer :ImageButton
    private lateinit var imageButtonRight :ImageButton
    private lateinit var imageButtonLeft :ImageButton
    private lateinit var spinner: Spinner
    private lateinit var textView : TextView
    private lateinit var recycleview : RecyclerView
    private lateinit var sOrientation : String
    private var listSequence = arrayListOf<String>()
    private val adapter = CustomAdapter(listSequence)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()

        val orientation: MutableList<String> = ArrayList()
        orientation.add("N")
        orientation.add("S")
        orientation.add("D")
        orientation.add("G")

        val dataAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, orientation)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = dataAdapter

        recycleview.layoutManager = LinearLayoutManager(baseContext)
        recycleview.adapter = adapter

        carte = Carte(this,5,4)
        carte.insertTresor(2,2,3)
        carte.insertTresor(1,2,5)
        carte.insertMontagne(0,2)
        carte.insertMontagne(1,3)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.buttonArriver -> {
                val s = StringBuilder()

                if(listSequence.isNotEmpty()){
                    for (i in 0 until listSequence.size)
                        s.append(listSequence[i]+"-")

                    carte.arriverAventurier(2, 1, sOrientation, s.toString())
                    fileDepart(carte)
                }
            }
            R.id.buttonDepart -> {
                carte.departAventurier(2, 1,true)
                fileDepart(carte)
            }
            R.id.imageButtonA -> listSequence.add("A")
            R.id.imageButtonD -> listSequence.add("D")
            R.id.imageButtonG -> listSequence.add("G")
        }
        adapter.notifyDataSetChanged()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        sOrientation = parent!!.getItemAtPosition(position).toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    private fun init (){
        buttonArriver= findViewById(R.id.buttonArriver)
        buttonDepart= findViewById(R.id.buttonDepart)
        imageButtonAvancer = findViewById(R.id.imageButtonA)
        imageButtonRight= findViewById(R.id.imageButtonD)
        imageButtonLeft = findViewById(R.id.imageButtonG)
        spinner = findViewById(R.id.spinnerOrientation)
        textView = findViewById(R.id.textResut)
        recycleview = findViewById(R.id.recycleview)

        buttonArriver.setOnClickListener(this)
        buttonDepart.setOnClickListener(this)
        imageButtonAvancer.setOnClickListener(this)
        imageButtonRight.setOnClickListener(this)
        imageButtonLeft.setOnClickListener(this)
        spinner.onItemSelectedListener = this
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

            val myReader = Scanner(file)
            while (myReader.hasNextLine()) {
                val data: String = myReader.nextLine()
                println(data)
            }
            myReader.close()

            toast("Saved see file in document")
        } catch (e: IOException) {
            e.printStackTrace()
        }catch(f : FileNotFoundException){
            f.printStackTrace()
        }
    }
}