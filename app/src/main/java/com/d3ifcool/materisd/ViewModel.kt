package com.d3ifcool.materisd

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewModel : ViewModel(){


    private val xcount= MutableLiveData<Int>()
    var count: LiveData<Int> = xcount

    init {

        xcount.value=0
    }

    fun tambahCount(){
        xcount.value=xcount.value?.plus(1)
    }


}