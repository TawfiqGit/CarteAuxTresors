package com.tawfiq.carteauxtresors.interfaces

interface Aventurier {

    fun arriverAventurier(axe_horizontal :Int , axe_vertical : Int, orientation : String,sq_mouvement: String)

    fun departAventurier(axe_horizontal :Int , axe_vertical : Int,isEnable : Boolean)
}