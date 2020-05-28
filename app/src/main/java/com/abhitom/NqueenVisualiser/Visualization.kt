package com.abhitom.NqueenVisualiser

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_visualization.*

class Visualization : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visualization)
        val sharedPreferences=this.getSharedPreferences("sharedPrefFile",Context.MODE_PRIVATE)
        noOfQueensTV.text=sharedPreferences.getInt("noOfQueens",0).toString()
        boardSizeTV.text=sharedPreferences.getInt("boardSize",0).toString()
    }
}
