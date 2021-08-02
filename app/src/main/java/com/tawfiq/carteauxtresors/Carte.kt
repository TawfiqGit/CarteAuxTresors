package com.tawfiq.carteauxtresors

import com.tawfiq.carteauxtresors.interfaces.Montagne
import com.tawfiq.carteauxtresors.interfaces.Tresor
import java.io.FileWriter

class Carte (private val largeur : Int, private val hauteur :Int) : Tresor, Montagne {

    private val carteTab : Array<Array<String>> = Array(hauteur) { Array(largeur){"*"}};

    fun showCarte(fileWriter : FileWriter){
        for (i in carteTab.indices) {
            for (j in carteTab[i].indices) {
                fileWriter.append(carteTab[i][j] + " ")
            }
            fileWriter.append(System.getProperty("line.separator"));
        }
        fileWriter.append(System.getProperty("line.separator"));
    }

    override fun insertTresor(axe_horizontal: Int, axe_vertical: Int, nb_tresor :Int) {
        carteTab[axe_horizontal][axe_vertical] = "T ($nb_tresor)"
    }

    override fun insertMontagne(axe_horizontal: Int, axe_vertical: Int) {
        carteTab[axe_horizontal][axe_vertical] = "M"
    }
}

