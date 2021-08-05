package com.tawfiq.carteauxtresors.interfaces

import android.content.Context
import android.widget.EditText
import android.widget.Toast

fun isEmpty(etText: EditText): Boolean {
    return etText.text.toString().trim { it <= ' ' }.isEmpty()
}

fun Context.toast(message : String, duration: Int = Toast.LENGTH_SHORT){
    Toast.makeText(this,message,duration)
        .show()
}