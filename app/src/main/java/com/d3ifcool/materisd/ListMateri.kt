package com.d3ifcool.materisd

import android.content.Intent
import android.os.Bundle
import android.renderscript.Sampler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class ListMateri:AppCompatActivity(),MateriClick{
    override fun onClicked(position: Int, kelas: Materi) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("pdf",arraymateri?.get(position)?.link)
        startActivity(intent)
    }

    private var db: DatabaseReference?=null
    private var arraymateri:ArrayList<Materi>?=null
    private var adapter:MateriAdapter?=null
    private var rv:RecyclerView?=null

    override fun onCreate(savedInstanceState: Bundle?) {

        val id = intent.extras?.getString("id")
        Log.i("idx",id!!)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.materi)
       arraymateri = arrayListOf()
        rv = findViewById(R.id.rv_materi)
        db=FirebaseDatabase.getInstance().getReference("Kelas").child(id).child("materi")
        adapter = MateriAdapter(this)
        rv?.setLayoutManager(LinearLayoutManager(this))
        rv?.adapter = adapter
        db?.addValueEventListener(object : ValueEventListener

        {


            override fun onDataChange(snapshot: DataSnapshot) {
              arraymateri?.clear()
                for (data in snapshot.children){
                    val materi = data.getValue(Materi::class.java)
                    Log.d("testt",materi?.link!!)
                    arraymateri?.add(materi!!)


                }
                adapter?.data = arraymateri as ArrayList<Materi>
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })



    }
}