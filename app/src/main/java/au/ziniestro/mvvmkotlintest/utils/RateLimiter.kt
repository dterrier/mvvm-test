package au.ziniestro.mvvmkotlintest.utils

import android.os.SystemClock
import android.util.ArrayMap
import java.util.concurrent.TimeUnit

class RateLimiter<in KEY>(timeout: Int, timeUnit: TimeUnit) {
    private val timestamp = ArrayMap<KEY, Long>()
    private val timeout = timeUnit.toMillis(timeout.toLong())

    @Synchronized
    fun shouldFetch(key: KEY): Boolean {
        val lastFetched: Long? = timestamp[key]
        val now = now()

        if(lastFetched == null || (now - lastFetched > timeout)) {
            timestamp[key] = now
            return true
        }

        return false
    }

    fun now() = SystemClock.uptimeMillis()

    @Synchronized
    fun reset(key: KEY) {
        timestamp.remove(key)
    }
}