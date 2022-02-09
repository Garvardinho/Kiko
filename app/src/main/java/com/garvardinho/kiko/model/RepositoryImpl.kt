package com.garvardinho.kiko.model

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

const val URL_NOW_PLAYING =
    "https://api.themoviedb.org/3/movie/now_playing?api_key=9f9ff549c14dba55067c6fecad30cd71&page=1"
const val URL_UPCOMING =
    "https://api.themoviedb.org/3/movie/upcoming?api_key=9f9ff549c14dba55067c6fecad30cd71&page=1"

class RepositoryImpl(private val movieLoadedListener: MovieLoadedListener) : Repository {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun loadMoviesFromServer() {
        try {
            val handler = Handler(Looper.getMainLooper())
            var nowPlayingMovies = MovieDTO(listOf())
            var upcomingMovies = MovieDTO(listOf())

            val t1 = Thread {
                nowPlayingMovies = loadMoviesFromUrl(URL_NOW_PLAYING)
            }.apply { start() }

            val t2 = Thread {
                upcomingMovies = loadMoviesFromUrl(URL_UPCOMING)
            }.apply { start() }

            t1.join()
            t2.join()

            if (nowPlayingMovies.results.isEmpty() || upcomingMovies.results.isEmpty()) {
                throw MalformedURLException()
            }

            handler.post {
                movieLoadedListener.onLoaded(nowPlayingMovies, upcomingMovies)
            }
        } catch (e: MalformedURLException) {
            Log.e("", "Fail URL", e)
            e.printStackTrace()
            movieLoadedListener.onFailed(e)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun loadMoviesFromUrl(url: String): MovieDTO {
        var urlConnection: HttpsURLConnection? = null
        try {
            urlConnection = URL(url).openConnection() as HttpsURLConnection
            urlConnection.requestMethod = "GET"
            urlConnection.readTimeout = 10000
            val bufferedReader = BufferedReader(InputStreamReader(urlConnection.inputStream))
            return Gson().fromJson(getLines(bufferedReader), MovieDTO::class.java)
        } catch (e: Exception) {
            Log.e("", "Fail connection", e)
            e.printStackTrace()
            movieLoadedListener.onFailed(e)
        } finally {
            urlConnection?.disconnect()
        }
        return MovieDTO(listOf())
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }
}