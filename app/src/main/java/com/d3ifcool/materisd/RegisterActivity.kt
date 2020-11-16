package com.d3ifcool.materisd

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class RegisterActivity :AppCompatActivity(){


    private var mRegister: Button? = null
    private var mRegisNamaUser: EditText? = null
    private var mRegisPass: EditText? = null
    private var mRegisEmail: EditText? = null
    private var auth: FirebaseAuth? = null
    lateinit var id:String
    lateinit var databaseReference:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)
        auth = FirebaseAuth.getInstance()
        mRegister = findViewById(R.id.bt_regis)
        mRegisNamaUser = findViewById(R.id.nama_et)
        mRegisPass = findViewById(R.id.password_et_regis)
        mRegisEmail = findViewById(R.id.email_et_regis)


        mRegister?.setOnClickListener{





            val nama = mRegisNamaUser?.text.toString().toLowerCase().trim()
            val email = mRegisEmail?.text.toString().toLowerCase().trim()
            val pass = mRegisPass?.text.toString().trim()
            if(TextUtils.isEmpty(nama)|| TextUtils.isEmpty(email)|| TextUtils.isEmpty(pass)){
                Toast.makeText(this@RegisterActivity, "Semua kolom harus terisi", Toast.LENGTH_SHORT).show()
            }else {
                auth!!.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this@RegisterActivity) { task ->
                        if (!task.isSuccessful) {
                            Toast.makeText(this@RegisterActivity, "Gagal Mendaftar, Harap Cek Koneksi Internet\n" + task.exception,
                                Toast.LENGTH_SHORT).show()
                        } else {
                            if (FirebaseAuth.getInstance().currentUser != null) {
                                id = FirebaseAuth.getInstance().currentUser!!.uid

                            }

                            databaseReference = FirebaseDatabase.getInstance().getReference("User").child(id!!)
                            val value = User(nama, email,)
                            databaseReference!!.setValue(value).addOnCompleteListener {
                                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                Toast.makeText(this@RegisterActivity, "Berhasil Mendaftar", Toast.LENGTH_SHORT).show()
                                startActivity(intent)
                                finish()
                            }

                        }
                    }
            }
        }




    }




}