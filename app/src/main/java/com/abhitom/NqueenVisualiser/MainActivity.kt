package com.abhitom.NqueenVisualiser

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.widget.AppCompatSeekBar
import com.sdsmdg.harjot.crollerTest.Croller
import com.sdsmdg.harjot.crollerTest.OnCrollerChangeListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.floor

class MainActivity : AppCompatActivity(), OnCrollerChangeListener {

    var noOfQueens=0
    var boardSize=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val sharedPreferences=this.getSharedPreferences("sharedPrefFile",Context.MODE_PRIVATE)
        val prefEditor=sharedPreferences.edit()

        noOfQueensSB.setOnCrollerChangeListener(this)
        boardSizeSB.setOnCrollerChangeListener(this)
        startVisualizationBTN.setOnClickListener {
            prefEditor.putInt("noOfQueens",noOfQueens)
            prefEditor.putInt("boardSize",boardSize)
            prefEditor.apply()
            prefEditor.commit()
            val intent = Intent(this,Visualization::class.java)
            startActivity(intent)
        }
    }

    override fun onProgressChanged(croller: Croller?, progress: Int) {
        if (croller == noOfQueensSB){
            showNoOfQueensTV.text = progress.toString()
            noOfQueens = progress
        }
        if(croller == boardSizeSB){
            showBoardSizeTV.text = progress.toString()
            boardSize = progress
        }
    }

    override fun onStartTrackingTouch(croller: Croller?) {}

    override fun onStopTrackingTouch(croller: Croller?) {}
}



