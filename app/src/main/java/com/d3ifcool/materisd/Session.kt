package com.d3ifcool.materisd

import android.content.Context
import android.content.SharedPreferences

class Session(var context:Context?){
   var pref:SharedPreferences
     var editor: SharedPreferences.Editor

    init {
        pref = context!!.getSharedPreferences("myapp",Context.MODE_PRIVATE)
        editor=pref.edit()
    }

    fun setLoggedin(loggedin:Boolean){
        editor.putBoolean("loggedInmode",loggedin)
        editor.commit()
    }

    fun loggedIn():Boolean{

        return pref.getBoolean("loggedInmode",false)
    }

}