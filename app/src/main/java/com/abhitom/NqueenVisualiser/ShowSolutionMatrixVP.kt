package com.abhitom.NqueenVisualiser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.gson.Gson

class ShowSolutionMatrixVP : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_solution_matrix_v_p)
        var bundle :Bundle ?=intent.extras
        var dataString = bundle!!.getString("solutionMatrix")
        var dataHolder= Gson().fromJson(dataString,solutionMatrix::class.java)
        Toast.makeText(this,dataHolder.data.size.toString(), Toast.LENGTH_SHORT).show()
    }
}
