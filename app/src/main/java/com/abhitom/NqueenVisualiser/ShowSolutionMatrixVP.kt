package com.abhitom.NqueenVisualiser

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.varunest.sparkbutton.SparkButton
import com.varunest.sparkbutton.SparkButtonBuilder
import kotlinx.android.synthetic.main.activity_show_solution_matrix_v_p.*

class ShowSolutionMatrixVP : AppCompatActivity() {

    private var mainPagerAdapter : MainPageAdapter = MainPageAdapter()
    private var boardSize:Int = 0
    val buttons: MutableList<MutableList<SparkButton>> = ArrayList()
    private lateinit var dataHolder:solutionMatrix

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_solution_matrix_v_p)
        val bundle :Bundle ?=intent.extras
        val dataString = bundle!!.getString("solutionMatrix")
        dataHolder = Gson().fromJson(dataString,solutionMatrix::class.java)
        Toast.makeText(this,dataHolder.data.size.toString(), Toast.LENGTH_SHORT).show()

        Log.d("TAG", dataString.toString())

        val sharedPreferences=this.getSharedPreferences("sharedPrefFile", Context.MODE_PRIVATE)
        boardSize=sharedPreferences.getInt("boardSize",0)

        mainPagerAdapter = MainPageAdapter()
        viewPager.adapter = mainPagerAdapter

        if(dataHolder.data.size <= 0 ){  //If no results available.
            val inflater: LayoutInflater = layoutInflater
            val v0: LinearLayout = inflater.inflate(R.layout.dummy_resource, null) as LinearLayout
            mainPagerAdapter.addView(v0, 0)
            mainPagerAdapter.notifyDataSetChanged()
        }
        else{
            // adding views to the ViewPager
            for(i in 0 until dataHolder.data.size){
                createButtonGrid()
                printResult(dataHolder.data[i]
                    //change here to next page
                }
        }
    }

    private fun printResult(mutableList: MutableList<MutableList<Int>>) {
        for (i in 0 until boardSize) {
            buttons[mutableList[i][0]][mutableList[i][1]].setInactiveImage(R.drawable.ic_crown)
        }
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

        val inflater: LayoutInflater = layoutInflater
        val v0: LinearLayout = inflater.inflate(R.layout.answer_layout, null) as LinearLayout
        v0.addView(mainScreen)

        for (i in 0 until boardSize) {

            val arrayLinearLayout = LinearLayout(this)
            arrayLinearLayout.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT,1.0f
            )
            arrayLinearLayout.orientation = LinearLayout.HORIZONTAL

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
        addView(v0)
    }


    private fun addView(newPage: View){
        val pageIndex: Int = mainPagerAdapter.addView(newPage)
        //viewPager.setCurrentItem(pageIndex, true)
        mainPagerAdapter.notifyDataSetChanged()
    }

    private fun removeView(newPage: View){
        var pageIndex: Int = mainPagerAdapter.removeView(viewPager, newPage)
       // if(pageIndex == mainPagerAdapter.count)
        //    pageIndex--
       // viewPager.currentItem = pageIndex
        mainPagerAdapter.notifyDataSetChanged()
    }

    private fun getCurrentPage(): View = mainPagerAdapter.getView(viewPager.currentItem)

    private fun setCurrentPage(pageToShow: View)=
        viewPager.setCurrentItem(mainPagerAdapter.getItemPosition(pageToShow), true)
}
