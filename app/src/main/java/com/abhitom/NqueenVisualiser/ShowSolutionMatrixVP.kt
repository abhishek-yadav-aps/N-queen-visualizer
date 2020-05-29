package com.abhitom.NqueenVisualiser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Toast
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_show_solution_matrix_v_p.*

class ShowSolutionMatrixVP : AppCompatActivity() {

    private var mainPagerAdapter : MainPageAdapter = MainPageAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_solution_matrix_v_p)
        val bundle :Bundle ?=intent.extras
        val dataString = bundle!!.getString("solutionMatrix")
        val dataHolder= Gson().fromJson(dataString,solutionMatrix::class.java)
        Toast.makeText(this,dataHolder.data.size.toString(), Toast.LENGTH_SHORT).show()

        mainPagerAdapter = MainPageAdapter()
        viewPager.adapter = mainPagerAdapter

        val inflater: LayoutInflater = layoutInflater
        val v0: LinearLayout = inflater.inflate(R.layout.dummy_resource, null) as LinearLayout
        mainPagerAdapter.addView(v0, 0)
        mainPagerAdapter.notifyDataSetChanged()

        // adding views to the ViewPager
        for(i in 0 until dataHolder.data.size){
            //Inflater me daal ke Linear view baana dena
            //aur add kardena function me.
            //addView()
        }
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
    }
    private fun getCurrentPage(): View = mainPagerAdapter.getView(viewPager.currentItem)

    private fun setCurrentPage(pageToShow: View)=
        viewPager.setCurrentItem(mainPagerAdapter.getItemPosition(pageToShow), true)
}
