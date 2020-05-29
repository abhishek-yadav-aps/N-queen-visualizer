package com.abhitom.NqueenVisualiser

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.varunest.sparkbutton.SparkButton
import com.varunest.sparkbutton.SparkButtonBuilder
import com.varunest.sparkbutton.SparkEventListener
import kotlinx.android.synthetic.main.activity_visualization.*

class Visualization : AppCompatActivity() {

    var noOfQueens=0
    var boardSize=0
    val buttons: MutableList<MutableList<SparkButton>> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visualization)

        val sharedPreferences=this.getSharedPreferences("sharedPrefFile",Context.MODE_PRIVATE)
        noOfQueens=sharedPreferences.getInt("noOfQueens",0)
        boardSize=sharedPreferences.getInt("boardSize",0)-1
        createButtonGrid()
    }
    private fun createButtonGrid() {
        // new dynamically declared linear layout inside screen linearlayout so grid can be deleted at any time
        val mainScreen = LinearLayout(this)
        mainScreen.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        mainScreen.orientation = LinearLayout.VERTICAL
        var mainScreenID = resources.getIdentifier("mainScreen", "id", packageName)
        mainScreen.id=mainScreenID
        screen.addView(mainScreen)
        for (i in 0..boardSize) {

            val arrayLinearLayout = LinearLayout(this)
            arrayLinearLayout.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT,1.0f
            )
            arrayLinearLayout.orientation = LinearLayout.HORIZONTAL
            //arrayLinearLayout.setPadding(1,1,1,1)

            val buttoncol: MutableList<SparkButton> = ArrayList()
            for (j in 0..boardSize) {
                val sbutton: SparkButton = SparkButtonBuilder(this).setImageSizeDp(30)
                    .setActiveImage(R.drawable.ic_crown) //after creation
                    .setInactiveImage(R.drawable.ic_mathematics_empty) //before ceration
                    .setPrimaryColor(
                        ContextCompat.getColor(
                            this,
                            android.R.color.holo_blue_dark
                        )
                    )
                    .setSecondaryColor(
                        ContextCompat.getColor(
                            this,
                            android.R.color.holo_green_dark
                        )
                    )
                    .build()
                sbutton.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    1.0f)
                buttoncol.add(sbutton)
                arrayLinearLayout.addView(sbutton)
            }

            buttons.add(buttoncol)

            mainScreen.addView(arrayLinearLayout)
        }
        //paintAllButtonsWhiteAgain()
    }
    private fun paintAllButtonsWhiteAgain() {
        for (i in 0..boardSize){
            for (j in 0..boardSize){
                buttons[i][j].setBackgroundColor(Color.parseColor("#FFFFFF"))
            }
        }
    }
}

