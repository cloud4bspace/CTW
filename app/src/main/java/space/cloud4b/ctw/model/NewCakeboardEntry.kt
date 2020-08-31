package space.cloud4b.ctw.model

import android.util.Log

object NewCakeboardEntry {
    var a: Int = 0
    var b: Int = 1
    var tac: String = ""
    var userEmail: String = ""
    var date: String = ""

    var time: String = ""

    var reason: String = ""
    var infos: String = ""
    var beverages: String = ""

    init {
        Log.i("NewCakeboardEntry", "init complete")
        // hier kann man ggf. auch tac und userEmail setzen..
    }


    fun makeMe12(): Int {
        a = 12
        return a
    }



}