package com.garvardinho.kiko.model

import MovieLoadedListener
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

const val URL_NOW_PLAYING = "https://api.themoviedb.org/3/movie/now_playing?api_key=9f9ff549c14dba55067c6fecad30cd71&page=1"
const val URL_UPCOMING = "https://api.themoviedb.org/3/movie/upcoming?api_key=9f9ff549c14dba55067c6fecad30cd71&page=1"
class RepositoryImpl(private val movieLoadedListener : MovieLoadedListener) : Repository {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun getMoviesFromServer() {
        try {
            val urlNowPlaying = URL(URL_NOW_PLAYING)
            val urlUpcoming = URL(URL_UPCOMING)
            val handler = Handler(Looper.getMainLooper())
            Thread {
                var urlConnection : HttpsURLConnection? = null
                try {
                    urlConnection = urlNowPlaying.openConnection() as HttpsURLConnection
                    urlConnection.requestMethod = "GET"
                    urlConnection.readTimeout = 10000
                    var bufferedReader = BufferedReader(InputStreamReader(urlConnection.inputStream))
                    val nowPlayingMovieDTO = Gson().fromJson(getLines(bufferedReader), MovieDTO::class.java)

                    urlConnection = urlUpcoming.openConnection() as HttpsURLConnection
                    urlConnection.requestMethod = "GET"
                    urlConnection.readTimeout = 10000
                    bufferedReader = BufferedReader(InputStreamReader(urlConnection.inputStream))
                    val upcomingMovieDTO = Gson().fromJson(getLines(bufferedReader), MovieDTO::class.java)

                    handler.post {
                        movieLoadedListener.onLoaded(nowPlayingMovieDTO, upcomingMovieDTO)
                    }
                } catch (e: Exception) {
                    Log.e("", "Fail connection", e)
                    e.printStackTrace()
                    movieLoadedListener.onFailed(e)
                } finally {
                    urlConnection?.disconnect()
                }
            }.start()
        } catch (e: MalformedURLException) {
            Log.e("", "Fail URL", e)
            e.printStackTrace()
            movieLoadedListener.onFailed(e)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }
}