package com.garvardinho.kiko.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.garvardinho.kiko.model.Repository
import com.garvardinho.kiko.model.RepositoryImpl
import java.lang.Thread.sleep

class MainViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl: Repository = RepositoryImpl()
) : ViewModel() {

    val liveData: LiveData<AppState>
        get() = liveDataToObserve

    fun getMoviesFromLocalResource() {
        liveDataToObserve.value = AppState.Loading
        Thread {
            sleep(2000)
            liveDataToObserve.postValue(AppState.Success(
                repositoryImpl.getMoviesFromServer(),
                repositoryImpl.getMoviesFromServer()))
        }.start()
    }
}