package com.tawfiq.carteauxtresors

import android.content.Context
import com.tawfiq.carteauxtresors.interfaces.Aventurier
import com.tawfiq.carteauxtresors.interfaces.Montagne
import com.tawfiq.carteauxtresors.interfaces.Tresor
import java.io.FileWriter
import java.util.*

class Carte (private val context : Context, private val largeur : Int, private val hauteur :Int) : Tresor, Montagne,Aventurier {

    private val carteTab : Array<Array<String>> = Array(hauteur) { Array(largeur){"."}};
    private var message: String ="";
    var keysTresor = 0
    var tresorMaps = HashMap<Int, Int>()

    fun showCarte(fileWriter : FileWriter){
        for (i in carteTab.indices) {
            for (j in carteTab[i].indices) {
                fileWriter.append(carteTab[i][j] + " ")
            }
            fileWriter.append(System.getProperty("line.separator"));
        }
        fileWriter.append(System.getProperty("line.separator"));
        fileWriter.append(message);
    }

    override fun insertTresor(axe_horizontal: Int, axe_vertical: Int, nb_tresor :Int) {
        //Save nbTresor one each insert
        keysTresor += 1
        tresorMaps[keysTresor] = nb_tresor;
        carteTab[axe_horizontal][axe_vertical] = "T($nb_tresor)"
    }

    override fun insertMontagne(axe_horizontal: Int, axe_vertical: Int) {
        carteTab[axe_horizontal][axe_vertical] = "M"
    }

    override fun arriverAventurier(axe_horizontal: Int, axe_vertical: Int, orientation: String, sq_mouvement: String ) {
        var axeAvancer = axe_horizontal
        var axeTourner = axe_vertical
        var tresorRamasser = 0

        for (s in sq_mouvement.split("-").toTypedArray()) {

            if(!isCheckMontagneDetected(axeAvancer,axeTourner)){

                if (isCanAvancer(axeAvancer)){
                    if (isCanTourner(axeTourner)){

                        when (s) {
                            "A" -> when(orientation){
                                "N" -> axeAvancer -= 1
                                "S" -> axeAvancer += 1
                                "G" -> axeTourner -= 1
                                "D" -> axeTourner += 1
                            }
                            "D"-> if (orientation == "N" || orientation == "S") axeTourner += 1
                                  else axeAvancer += 1

                            "G" -> if (orientation == "N" || orientation == "S") axeTourner -= 1
                                   else axeAvancer -= 1
                        }

                        for (i in 1..tresorMaps.size){

                            if (carteTab[axeAvancer][axeTourner] == "T(${tresorMaps[i]})"){
                                tresorRamasser += tresorMaps[i]!!
                            }
                        }
                    }
                }
            }else{
                break
            }
        }

        if (!isCheckMontagneDetected(axeAvancer,axeTourner)){
            departAventurier(axe_horizontal,axe_vertical,false)
            carteTab[axeAvancer][axeTourner] = "A ($tresorRamasser)"
        }
    }

    override fun departAventurier(axe_horizontal: Int, axe_vertical: Int, isEnable : Boolean) {
        if (isEnable){
            carteTab[axe_horizontal][axe_vertical] = "A"
        }else {
            carteTab[axe_horizontal][axe_vertical] = ""
        }
    }

    private fun isCanAvancer(sq_mouvement : Int ):Boolean{
        if (sq_mouvement == 0){
            return false
        }
        return true
    }

    private fun isCanTourner(sq_mouvement : Int ):Boolean{
        if (sq_mouvement == 0){
            return false
        }
        return true
    }

    private fun isCheckMontagneDetected (axeAvancer : Int, axeTourner: Int) : Boolean {
        return if(carteTab[axeAvancer][axeTourner] == "M"){
            message = context.getString(R.string.text_impossible_montagne)
            true
        }else{
            false
        }
    }
}
