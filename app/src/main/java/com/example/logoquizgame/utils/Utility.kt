package com.example.logoquizgame.utils

import android.content.Context
import java.io.IOException

object Utility {
    fun readXMLinString(fileName: String?, c: Context): String {
        return try {
            val `is` = c.assets.open(fileName!!)
            var size = 0
            try {
                size = `is`.available()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            String(buffer)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }
}