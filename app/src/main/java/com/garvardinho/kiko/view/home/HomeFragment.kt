package com.garvardinho.kiko.view.home

import android.app.SearchManager
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.garvardinho.kiko.R
import com.garvardinho.kiko.databinding.HomeFragmentBinding
import com.garvardinho.kiko.model.Movie
import com.garvardinho.kiko.view.home.recyclerviews.KOnItemClickListener
import com.garvardinho.kiko.view.home.recyclerviews.MovieListSourceImpl
import com.garvardinho.kiko.view.home.recyclerviews.adapters.NowPlayingMoviesAdapter
import com.garvardinho.kiko.view.home.recyclerviews.adapters.UpcomingMoviesAdapter
import com.garvardinho.kiko.view.openFragment
import com.garvardinho.kiko.viewmodel.AppState
import com.garvardinho.kiko.viewmodel.MainViewModel

class HomeFragment : Fragment() {

    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by lazy { ViewModelProvider(this)[MainViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.liveData.observe(viewLifecycleOwner, { renderData(it) })
        viewModel.getMoviesFromLocalResource()
        requireActivity().actionBar?.setDisplayHomeAsUpEnabled(false)
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                setMoviesData(ArrayList(appState.nowPlayingMoviesData), true)
                setMoviesData(ArrayList(appState.upcomingMoviesData), false)
                binding.homeFragmentContent.visibility = View.VISIBLE
                binding.loadingIndicator.visibility = View.GONE
            }
            is AppState.Loading -> {
                binding.loadingIndicator.visibility = View.VISIBLE
                binding.homeFragmentContent.visibility = View.GONE
            }
            is AppState.Error -> {
                binding.loadingIndicator.visibility = View.GONE
                binding.homeFragmentContent.visibility = View.GONE
            }
        }
    }

    private fun setMoviesData(moviesData: ArrayList<Movie>, isNowPlaying: Boolean) {
        val layoutManager = LinearLayoutManager(context)
        val data = MovieListSourceImpl(moviesData)
        val adapter =
            if (isNowPlaying) NowPlayingMoviesAdapter(data)
            else UpcomingMoviesAdapter(data)
        val recyclerView: RecyclerView =
            if (isNowPlaying) binding.nowPlayingView
            else binding.upcomingView

        adapter.setOnItemClickListener(object : KOnItemClickListener {
            override fun setListener(v: View, position: Int) {
                requireActivity().supportFragmentManager
                    .openFragment(MovieDetailsFragment.newInstance(data.getCardData(position)))
            }
        })

        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_app_bar, menu)
        val searchItem: MenuItem? = menu.findItem(R.id.action_search)
        val searchManager: SearchManager = requireActivity()
            .getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView: SearchView = searchItem
            ?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        searchView.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(text: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    return false
                }
            }
        )
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}