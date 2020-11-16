package com.d3ifcool.materisd

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class QuizActivity :AppCompatActivity (){


    var soal: ArrayList<Soal>? = null
    var jawaban: String? = null
    var jawabanArray: ArrayList<String>? = null
    var counter = 0
    var xcore = 0
    var button_mulai: Button? = null
    val database = FirebaseDatabase.getInstance().reference
    var tvsoal: TextView? = null
    var soalnya: TextView?=null
    var buttonNext: Button? = null
    var buttonPrev: Button? = null
    var radiogrup: RadioGroup? = null
    var radio1: RadioButton? = null
    var radio2: RadioButton? = null
    var radio3: RadioButton? = null
    var noSoal: TextView? = null
    var back: Boolean=true
    private lateinit var viewmodel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quiz_activity)
        jawabanArray = arrayListOf()

        soal= arrayListOf()
        tvsoal = findViewById(R.id.tv_soal)
        radiogrup = findViewById(R.id.radioGrup)
        radio1 = findViewById(R.id.radio1)
        radio2 = findViewById(R.id.radio2)
        radio3 = findViewById(R.id.radio3)
        noSoal = findViewById(R.id.soalKe)

        radiogrup?.visibility=View.INVISIBLE
        noSoal?.visibility=View.INVISIBLE


        viewmodel = ViewModelProviders.of(this).get(ViewModel::class.java)

        viewmodel.count.observe(this, Observer { count->
            counter=count
            if(count==soal?.size?.minus(1)){
                button_mulai?.text = "Finish"

            }

        })



        button_mulai=findViewById(R.id.mulai_quiz)

        button_mulai?.visibility=View.GONE


        button_mulai?.setOnClickListener {
            if (button_mulai?.text!!.equals("Finish")) {
                checkJawaban()
                val nilai = (xcore*100)/soal?.size!!
                val builder = AlertDialog.Builder(this)


                        .setMessage("Nilai       : $nilai")
                        .setPositiveButton("Oke") { dialog, which ->
                            val intent = Intent(this, ListKelas::class.java)

                            startActivity(intent)
                            finishAffinity()


                        }
                builder.setCancelable(false)

                builder.create().show()


            }

            else{


                    checkJawaban()
                    viewmodel.tambahCount()
                    setView()
                    noSoal?.text = "Soal : " + (counter + 1).toString() + "/" + soal?.size.toString()





            }
        }



        database.child("Soal 1").addValueEventListener(object : ValueEventListener{


            override fun onDataChange(snapshot: DataSnapshot) {
                for (data in snapshot.children){
                val s = data.getValue(Soal::class.java)
                    soal?.add(s!!)


                }

                setView()
                noSoal?.text= "Soal : " + (counter + 1).toString() + "/" + soal?.size.toString()

                radiogrup?.visibility=View.VISIBLE
                noSoal?.visibility=View.VISIBLE

                button_mulai?.visibility=View.VISIBLE

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }


    fun checkJawaban() {

        if (radio1?.isChecked!!) {

            if (radio1?.text.toString().equals(jawaban)) {
                xcore++
            }
        }
        if (radio2?.isChecked!!) {

            if (radio2?.text.toString().equals(jawaban)) {
                xcore++
            }
        }
        if (radio3?.isChecked!!) {

            if (radio3?.text.toString().equals(jawaban)) {
              xcore++
            }
        }



    }


    fun setView() {

        radio1?.isChecked = true
        tvsoal?.text = soal?.get(counter)?.soal
        soal?.get(counter)?.pilihan?.shuffle()
        radio1?.text = soal?.get(counter)?.pilihan?.get(0)
        radio2?.text = soal?.get(counter)?.pilihan?.get(1)
        radio3?.text = soal?.get(counter)?.pilihan?.get(2)
        jawaban = soal?.get(counter)?.jawaban


    }
}