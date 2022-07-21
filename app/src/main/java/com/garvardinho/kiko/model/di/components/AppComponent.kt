package com.garvardinho.kiko.model.di.components

import com.garvardinho.kiko.model.di.modules.*
import com.garvardinho.kiko.presenter.details.MovieDetailsViewPresenter
import com.garvardinho.kiko.presenter.favorites.FavoritesViewPresenter
import com.garvardinho.kiko.presenter.home.HomeViewPresenter
import com.garvardinho.kiko.presenter.toprated.TopRatedViewPresenter
import com.garvardinho.kiko.view.MainActivity
import com.garvardinho.kiko.view.details.MovieDetailsFragment
import com.garvardinho.kiko.view.favorites.FavoritesFragment
import com.garvardinho.kiko.view.home.HomeFragment
import com.garvardinho.kiko.view.toprated.TopRatedFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        CiceroneModule::class,
        DaoModule::class,
        RealmDataModule::class,
        RemoteDataModule::class,
        RepositoryModule::class
    ]
)
interface AppComponent {
    fun inject(mainActivity: MainActivity)

    fun inject(homeFragment: HomeFragment)
    fun inject(favoritesFragment: FavoritesFragment)
    fun inject(topRatedFragment: TopRatedFragment)

    fun inject(homeViewPresenter: HomeViewPresenter)
    fun inject(favoritesViewPresenter: FavoritesViewPresenter)
    fun inject(topRatedViewPresenter: TopRatedViewPresenter)
    fun inject(detailsViewPresenter: MovieDetailsViewPresenter)
}