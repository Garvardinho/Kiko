import com.garvardinho.kiko.model.MovieDTO

interface MovieLoadedListener {
    fun onLoaded(movieDTOS: MovieDTO)
    fun onFailed(throwable: Throwable)
}