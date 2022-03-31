package com.android.willyrrhh

import org.junit.Test

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URL
import java.net.URLConnection
import java.nio.charset.Charset

class ApiConnectionTest {

    private val OOMPA_LOOMPA = "oompa-loompas"
    private val SEPARATION = "/"
    private val VALID_ID = 1

    @Test
    @Throws(Exception::class)
    fun testAvailabilityApiResults() {
        val urlConnection: URLConnection =
            URL(BuildConfig.API_BASE_URL+OOMPA_LOOMPA).openConnection()
        val response: InputStream = urlConnection.getInputStream()
        val stringBuffer = StringBuffer()
        BufferedReader(InputStreamReader(response, Charset.defaultCharset())).use { reader ->
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                stringBuffer.append(line)
            }
        }
        assert(stringBuffer.isNotEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun testAvailabilityResultId() {
        val urlConnection: URLConnection =
            URL(BuildConfig.API_BASE_URL+ OOMPA_LOOMPA+ SEPARATION + VALID_ID).openConnection()
        val response: InputStream = urlConnection.getInputStream()
        val stringBuffer = StringBuffer()
        BufferedReader(InputStreamReader(response, Charset.defaultCharset())).use { reader ->
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                stringBuffer.append(line)
            }
        }
        assert(stringBuffer.isNotEmpty())
    }
}