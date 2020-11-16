package com.d3ifcool.materisd


class Kelas {
    var nama: String? = null
    var materi: ArrayList<Materi>?= null


    constructor() {}

    constructor(
        nama: String?,

       materi: ArrayList<Materi>

    ) {
        this.nama = nama

        this.materi = materi


    }
}