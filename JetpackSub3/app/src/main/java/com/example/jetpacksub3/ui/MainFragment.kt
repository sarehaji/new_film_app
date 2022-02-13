package com.example.jetpacksub3.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jetpacksub3.adapter.RecyclerViewAdapter
import com.example.jetpacksub3.data.source.DataFilm
import com.example.jetpacksub3.databinding.FragmentMainBinding
import com.example.jetpacksub3.viewmodel.MainViewModel
import com.example.jetpacksub3.viewmodel.ViewModelFactory

class MainFragment : Fragment() {

    private lateinit var fragmentMainBinding: FragmentMainBinding

    private lateinit var mainViewModel: MainViewModel
    private lateinit var factory: ViewModelFactory
    private lateinit var adapter: RecyclerViewAdapter
    private var state: Int? = null

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"

        @JvmStatic
        fun newInstance(sectionNumber: Int): MainFragment {
            return MainFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        fragmentMainBinding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return fragmentMainBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fragmentMainBinding.recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = RecyclerViewAdapter()
        fragmentMainBinding.recyclerView.adapter = adapter
        adapter.setOnItemClickCallback(object : RecyclerViewAdapter.OnItemClickCallback{
            override fun onItemClicked(data: DataFilm) {
                toDetail(data)
            }
        })

        state = arguments?.getInt(ARG_SECTION_NUMBER)
        factory = ViewModelFactory.getInstance(requireActivity())
        mainViewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
        if (state == 2){
            mainViewModel.getShows().observe(viewLifecycleOwner, Observer { shows ->
                if (shows != null) setAdapter(shows)
            })
        }
        else{
            mainViewModel.getMovies().observe(viewLifecycleOwner, Observer { movies ->
                if (movies != null) setAdapter(movies)
            })
        }
    }

    private fun setAdapter(listFilm: List<DataFilm>){
        fragmentMainBinding.mainBar.visibility = View.GONE
        fragmentMainBinding.recyclerView.visibility = View.VISIBLE
        adapter.setData(listFilm)
        adapter.notifyDataSetChanged()
    }

    private fun toDetail(data: DataFilm){
        val intent = Intent(activity, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_DATA, data)
        intent.putExtra(DetailActivity.EXTRA_STATE, state)
        startActivity(intent)
    }
}