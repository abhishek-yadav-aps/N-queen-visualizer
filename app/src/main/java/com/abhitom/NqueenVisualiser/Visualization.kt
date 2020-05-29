package com.abhitom.NqueenVisualiser

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.varunest.sparkbutton.SparkButton
import com.varunest.sparkbutton.SparkButtonBuilder
import kotlinx.android.synthetic.main.activity_visualization.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Visualization : AppCompatActivity() {

    var boardSize=0
    val buttons: MutableList<MutableList<SparkButton>> = ArrayList()
    var ld= mutableListOf<Int>()
    var rd= mutableListOf<Int>()
    var cl= mutableListOf<Int>()
    var dataHolder:solutionMatrix=solutionMatrix()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visualization)

        val sharedPreferences=this.getSharedPreferences("sharedPrefFile",Context.MODE_PRIVATE)
        boardSize=sharedPreferences.getInt("boardSize",0)
        createButtonGrid()
        GlobalScope.launch(Dispatchers.Main) {
            for (i in 0..30){
                ld.add(0)
                rd.add(0)
                cl.add(0)
            }
            solveNQ()
        }
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
        for (i in 0 until boardSize) {

            val arrayLinearLayout = LinearLayout(this)
            arrayLinearLayout.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT,1.0f
            )
            arrayLinearLayout.orientation = LinearLayout.HORIZONTAL
            //arrayLinearLayout.setPadding(1,1,1,1)

            val buttoncol: MutableList<SparkButton> = ArrayList()
            for (j in 0 until boardSize) {
                val sbutton: SparkButton = SparkButtonBuilder(this).setImageSizeDp(30)
                    .setActiveImage(R.drawable.ic_crown) //after creation
                    .setInactiveImage(R.drawable.ic_mathematics_empty) //before creation
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
                sbutton.pressOnTouch(false)
                sbutton.isEnabled=false
                buttoncol.add(sbutton)
                arrayLinearLayout.addView(sbutton)
            }
            buttons.add(buttoncol)
            mainScreen.addView(arrayLinearLayout)
        }
    }

    suspend fun printSolution(board: Array<IntArray>) {
        var dataOfOneMatrix:MutableList<MutableList<Int>> = mutableListOf()
        for (i in 0 until boardSize) {
            for (j in 0 until boardSize)
            {
                if(board[i][j]==1){
                    var dataOfOneBox:MutableList<Int> = mutableListOf()
                    dataOfOneBox.add(i)
                    dataOfOneBox.add(j)
                    buttons[i][j].playAnimation()
                    buttons[i][j].setInactiveImage(R.drawable.ic_crown)
                    dataOfOneMatrix.add(dataOfOneBox)
                }
            }
        }
        dataHolder.data.add(dataOfOneMatrix)
        delay(1000)
        Toast.makeText(this,"DONE",Toast.LENGTH_SHORT).show()
    }

    suspend fun solveNQUtil(board: Array<IntArray>, col: Int): Boolean {
        if (col == boardSize) {
            var job1=GlobalScope.launch(Dispatchers.Main) {
                printSolution(board)
            }
            job1.join()
            return true
        }

        var res = false
        for (i in 0 until boardSize) {
            if ((ld[i - col + boardSize - 1] != 1 && rd[i + col] != 1) && cl[i] != 1) {
                board[i][col] = 1
                ld[i - col + boardSize - 1] = 1
                rd[i + col] = 1
                cl[i] = 1
                buttons[i][col].playAnimation()
                buttons[i][col].setInactiveImage(R.drawable.ic_crown)
                delay(100)
                var job2=GlobalScope.launch(Dispatchers.Main) {
                    res = solveNQUtil(board, col + 1) || res
                }
                job2.join()
                board[i][col] = 0
                ld[i - col + boardSize - 1] = 0
                rd[i + col] = 0
                cl[i] = 0
                buttons[i][col].playAnimation()
                buttons[i][col].setInactiveImage(R.drawable.ic_mathematics_empty)
                delay(100)
            }
        }
        return res
    }

    suspend fun solveNQ() {
        val board =
            Array(boardSize) { IntArray(boardSize) }
        if (solveNQUtil(board, 0) == false) {
            Toast.makeText(this,"NO SOLUTION",Toast.LENGTH_SHORT).show()
            return
        }
        var dataString= Gson().toJson(dataHolder)
        val intent= Intent(this,ShowSolutionMatrixVP::class.java)
        intent.putExtra("solutionMatrix",dataString)
        startActivity(intent)
        return
    }
}

