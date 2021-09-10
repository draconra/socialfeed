package com.aditya.socialfeed.util

import android.os.Build
import android.os.StrictMode
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

object CommonUtil {

    @Throws(IOException::class)
    fun getContentType(urlString: String?): String? {
        val url = URL(urlString)
        val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "HEAD"
        if (isRedirect(connection.responseCode)) {
            val newUrl: String =
                connection.getHeaderField("Location")
            return getContentType(newUrl)
        }
        return connection.contentType
    }

    private fun isRedirect(statusCode: Int): Boolean {
        if (statusCode != HttpURLConnection.HTTP_OK) {
            if (statusCode == HttpURLConnection.HTTP_MOVED_TEMP ||
                statusCode == HttpURLConnection.HTTP_MOVED_PERM
                || statusCode == HttpURLConnection.HTTP_SEE_OTHER
            ) {
                return true
            }
        }
        return false
    }

    fun enableStrictMode() {
        val sdkVersion = Build.VERSION.SDK_INT
        if (sdkVersion > 8) {
            val policy = StrictMode.ThreadPolicy.Builder()
                .permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }
    }
}