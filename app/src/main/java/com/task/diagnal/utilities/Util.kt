package com.task.diagnal.utilities

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import java.io.InputStream
import kotlin.math.roundToInt

class Util {

    companion object {

        fun readJSONFromAsset(context: Context, jsonFileName: String): String? {
            var json: String? = null
            try {
                val inputStream: InputStream = context.assets.open(jsonFileName)
                json = inputStream.bufferedReader().use { it.readText() }
            } catch (ex: Exception) {
                ex.printStackTrace()
                return null
            }
            return json
        }


        fun dpToPx(context: Context, dp: Int): Int {
            val r = context.resources
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), r.displayMetrics
            ).roundToInt()
        }

        fun getFileName(pageCount: Int): String = "CONTENTLISTINGPAGE-PAGE$pageCount.json"

    }
}