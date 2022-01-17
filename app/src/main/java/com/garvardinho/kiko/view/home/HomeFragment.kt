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
import com.garvardinho.kiko.viewmodel.AppState
import com.garvardinho.kiko.viewmodel.MainViewModel

class HomeFragment : Fragment() {

    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel

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
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.liveData.observe(viewLifecycleOwner, { renderData(it) })
        viewModel.getMoviesFromLocalResource()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                binding.loadingIndicator.visibility = View.GONE
                binding.homeFragmentContent.visibility = View.VISIBLE
                val nowPlayingMoviesData: ArrayList<Movie> = ArrayList(appState.nowPlayingMoviesData)
                val upcomingMoviesData: ArrayList<Movie> = ArrayList(appState.upcomingMoviesData)
                setNowPlayingMoviesData(nowPlayingMoviesData)
                setUpcomingMoviesData(upcomingMoviesData)
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

    private fun setNowPlayingMoviesData(nowPlayingMoviesData: ArrayList<Movie>) {
        val layoutManager = LinearLayoutManager(context)
        val data = MovieListSourceImpl(nowPlayingMoviesData)
        val adapter = NowPlayingMoviesAdapter(data)
        val nowPlayingMoviesRecyclerView: RecyclerView = binding.nowPlayingView

        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        nowPlayingMoviesRecyclerView.setHasFixedSize(true)
        nowPlayingMoviesRecyclerView.layoutManager = layoutManager
        nowPlayingMoviesRecyclerView.adapter = adapter
    }

    private fun setUpcomingMoviesData(upcomingMoviesData: ArrayList<Movie>) {
        val layoutManager = LinearLayoutManager(context)
        val data = MovieListSourceImpl(upcomingMoviesData)
        val adapter = UpcomingMoviesAdapter(data)
        val nowPlayingMoviesRecyclerView: RecyclerView = binding.upcomingView

        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        nowPlayingMoviesRecyclerView.setHasFixedSize(true)
        nowPlayingMoviesRecyclerView.layoutManager = layoutManager
        nowPlayingMoviesRecyclerView.adapter = adapter
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