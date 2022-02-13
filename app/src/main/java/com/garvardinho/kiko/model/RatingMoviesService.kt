package com.garvardinho.kiko.model

import android.app.Service
import android.content.Intent
import android.os.*
import androidx.annotation.RequiresApi
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.garvardinho.kiko.view.ratings.*
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.MalformedURLException
import java.net.URL
import java.util.ArrayList
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

const val URL_RATINGS =
    "https://api.themoviedb.org/3/movie/top_rated?api_key=9f9ff549c14dba55067c6fecad30cd71&page=1"

class RatingMoviesService : Service() {

    private val broadcastIntent = Intent(DETAILS_INTENT_FILTER)

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        loadRatingsMoviesFromServer()
        return START_NOT_STICKY
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun loadRatingsMoviesFromServer() {
        try {
            var ratingsMovies: MovieDTO
            val handler = Handler(Looper.getMainLooper())

            Thread {
                var urlConnection: HttpsURLConnection? = null
                try {
                    urlConnection = URL(URL_RATINGS).openConnection() as HttpsURLConnection
                    urlConnection.requestMethod = "GET"
                    urlConnection.readTimeout = 10000
                    val bufferedReader = BufferedReader(InputStreamReader(urlConnection.inputStream))
                    ratingsMovies = Gson().fromJson(getLines(bufferedReader), MovieDTO::class.java)
                    handler.post { onResponse(ratingsMovies) }
                } catch (e: Exception) {
                    handler.post { onErrorRequest(e.message ?: "Empty error") }
                    e.printStackTrace()
                } finally {
                    urlConnection?.disconnect()
                }
            }.apply { start() }
        } catch (e: MalformedURLException) {
            onMalformedUrl()
            e.printStackTrace()
        }
    }

    private fun onMalformedUrl() {
        putLoadResult(DETAILS_URL_MALFORMED_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onErrorRequest(error: String) {
        putLoadResult(DETAILS_REQUEST_ERROR_EXTRA)
        broadcastIntent.putExtra(DETAILS_REQUEST_ERROR_MESSAGE_EXTRA, error)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onResponse(movieDTO: MovieDTO?) {
        if (movieDTO == null) {
            onEmptyResponse()
        }
        else
            onSuccessResponse(movieDTO)
    }

    private fun onEmptyResponse() {
        putLoadResult(DETAILS_RESPONSE_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onSuccessResponse(movieDTO: MovieDTO) {
        if (movieDTO.results.isEmpty()) {
            onDataEmpty()
        }
        else {
            putLoadResult(DETAILS_RESPONSE_SUCCESS_EXTRA)
            broadcastIntent.putParcelableArrayListExtra(
                DETAILS_MOVIES_EXTRA,
                movieDTO.results as ArrayList<out Parcelable>)
            LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
        }
    }

    private fun onDataEmpty() {
        putLoadResult(DETAILS_DATA_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun putLoadResult(result: String) {
        broadcastIntent.putExtra(DETAILS_LOAD_RESULT_EXTRA, result)
    }
}