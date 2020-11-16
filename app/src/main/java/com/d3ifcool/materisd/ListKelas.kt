package com.d3ifcool.materisd

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ListKelas :AppCompatActivity(),RecycleListClickListener{
    private var db:DatabaseReference?=null
    private var kelas:ArrayList<Kelas>?=null
    private var arrayID:ArrayList<String>?=null
    private var rv:RecyclerView?=null
    private var adapter:KelasAdapter?=null
    val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        kelas = arrayListOf()
        arrayID = arrayListOf()
        setContentView(R.layout.kelas)
        rv=findViewById(R.id.rv_kelas)
        val linear= LinearLayoutManager(this)
        rv?.setLayoutManager(linear)
        adapter = KelasAdapter(this)
        rv?.adapter =adapter
        db=FirebaseDatabase.getInstance().getReference("Kelas")

        val id=db!!.push().key

        val data = Materi("Bahasa Indonesia","test")
        val data1 = Materi("Bahasa Yudaisme","bani israel")
        val materi= arrayListOf<Materi>()
        materi.add(data)
        materi.add(data1)
        val kelasx = Kelas("Kelas 1 SD",materi)
       // db!!.child(id!!).setValue(kelasx)




        db?.addValueEventListener(object :ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                kelas?.clear()
                for (data in snapshot.children) {
                    val id=data.key
                    val kelasny = data.getValue(Kelas::class.java)
                    kelas?.add(kelasny!!)
                    arrayID?.add(id!!)

                }
                if (kelas != null) {
                    adapter?.data = kelas!!
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


    }

    override fun onClicked(position: Int, kelas: Kelas) {
        val intent = Intent(this, ListMateri::class.java)
        intent.putExtra("id",arrayID?.get(position))
        Log.i("IDX", arrayID?.get(position)!!)

        startActivity(intent)

    }
}