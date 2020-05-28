package com.abhitom.NqueenVisualiser

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.widget.AppCompatSeekBar
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.floor

class MainActivity : AppCompatActivity(), OnSeekBarChangeListener {

    var noOfQueens=0
    var boardSize=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sharedPreferences=this.getSharedPreferences("sharedPrefFile",Context.MODE_PRIVATE)
        val prefEditor=sharedPreferences.edit()
        noOfQueensSB.setOnSeekBarChangeListener(this)
        boardSizeSB.setOnSeekBarChangeListener(this)
        startVisualizationBTN.setOnClickListener {
            prefEditor.putInt("noOfQueens",noOfQueens)
            prefEditor.putInt("boardSize",boardSize)
            prefEditor.apply()
            prefEditor.commit()
            val intent = Intent(this,Visualization::class.java)
            startActivity(intent)
        }
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        if(seekBar==noOfQueensSB){
            noOfQueens=progress/12
            showNoOfQueensTV.text= (progress/12).toString()
        }
        if(seekBar==boardSizeSB){
            boardSize=progress/12
            showBoardSizeTV.text= (progress/12).toString()
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {}

    override fun onStopTrackingTouch(seekBar: SeekBar?) {}
}



