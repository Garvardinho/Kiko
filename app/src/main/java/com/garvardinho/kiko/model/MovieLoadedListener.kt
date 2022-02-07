import com.garvardinho.kiko.model.MovieDTO

interface MovieLoadedListener {
    fun onLoaded(nowPlayingMovieDTOS: MovieDTO, upcomingMovieDTOS: MovieDTO)
    fun onFailed(throwable: Throwable)
}