package au.ziniestro.mvvmkotlintest.db

import android.util.Log
import androidx.room.TypeConverter

object GithubTypeConverters {
    @TypeConverter
    @JvmStatic
    fun stringToIntList(data: String?): List<Int>? {
        return data?.let {
            it.split(",").map {
                try {
                    it.toInt()
                } catch (e: NumberFormatException) {
                    Log.d("TAG", "Not conversion possible")
                    null
                }
            }?.filterNotNull()
        }
    }

    @TypeConverter
    @JvmStatic
    fun intListToString(ints: List<Int>?): String? {
        return ints?.joinToString(",")
    }
}