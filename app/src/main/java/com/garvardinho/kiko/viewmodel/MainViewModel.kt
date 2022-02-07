package com.garvardinho.kiko.viewmodel

import MovieLoadedListener
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.garvardinho.kiko.model.MovieDTO
import com.garvardinho.kiko.model.Repository
import com.garvardinho.kiko.model.RepositoryImpl

class MainViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl: Repository = RepositoryImpl(object : MovieLoadedListener {
        override fun onLoaded(nowPlayingMovieDTOS: MovieDTO, upcomingMovieDTOS: MovieDTO) {
            liveDataToObserve.value = AppState.Success(nowPlayingMovieDTOS.results, upcomingMovieDTOS.results)
        }

        override fun onFailed(throwable: Throwable) {
            throwable.printStackTrace()
        }

    }),
) : ViewModel() {

    val liveData: LiveData<AppState>
        get() = liveDataToObserve

    fun getMoviesFromServer() {
        liveDataToObserve.value = AppState.Loading
        repositoryImpl.getMoviesFromServer()
    }
}