package com.d3ifcool.materisd

class Soal {

    var jawaban:String?=null
    var soal:String?=null
    var pilihan:MutableList<String>?=null

    constructor(){}
    constructor( test:String, test1:MutableList<String>,  jawab:String?){
        this.soal=test
        this.pilihan=test1
        this.jawaban=jawab
    }

}