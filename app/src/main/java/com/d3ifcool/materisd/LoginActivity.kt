package com.d3ifcool.materisd

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class LoginActivity:AppCompatActivity(){
    private var email:EditText?=null
    private var pass:EditText?=null
    private var bt_login:Button?=null
    private var regis:TextView?=null
    private var session:Session?=null
    private var auth:FirebaseAuth?=null
    private var db:FirebaseDatabase?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        session= Session(this)
        if(session!!.loggedIn()){
            startActivity(Intent(this@LoginActivity,ListKelas::class.java))
            finish()
        }

        email = findViewById(R.id.email_et)
        pass = findViewById(R.id.password_et)
        bt_login = findViewById(R.id.bt_login)
        regis = findViewById(R.id.tv_regis)

        regis?.setOnClickListener {
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
         
        }
        auth= FirebaseAuth.getInstance()

        bt_login?.setOnClickListener {
            login()
        }




    }




    private fun login(){

        val mail = email?.text.toString().toLowerCase().trim()
        val passw = pass?.text.toString()

        if(TextUtils.isEmpty(mail)||TextUtils.isEmpty(passw)){
            Toast.makeText(this@LoginActivity,"Field tidak boleh kosong",Toast.LENGTH_LONG).show()
            return
        }else{

            auth!!.signInWithEmailAndPassword(mail, passw).addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Toast.makeText(this@LoginActivity, "Gagal Login", Toast.LENGTH_LONG).show()

                } else {

                    session!!.setLoggedin(true)
                    val intent = Intent(this@LoginActivity, ListKelas::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                }
            }

        }


    }
}