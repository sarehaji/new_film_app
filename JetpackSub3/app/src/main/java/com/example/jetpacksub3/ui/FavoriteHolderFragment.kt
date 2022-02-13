package com.example.jetpacksub3.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jetpacksub3.adapter.FavoriteRecyclerAdapter
import com.example.jetpacksub3.data.source.DataFilm
import com.example.jetpacksub3.databinding.FragmentFavoriteBinding
import com.example.jetpacksub3.viewmodel.FavoriteViewModel
import com.example.jetpacksub3.viewmodel.ViewModelFactory

class FavoriteHolderFragment : Fragment() {
    private lateinit var fragmentFavoriteBinding: FragmentFavoriteBinding

    private lateinit var viewModel: FavoriteViewModel
    private lateinit var factory: ViewModelFactory
    private lateinit var adapter: FavoriteRecyclerAdapter
    private var state: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentFavoriteBinding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return fragmentFavoriteBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        state = arguments?.getInt(ARG_SECTION_NUMBER)

        fragmentFavoriteBinding.recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = FavoriteRecyclerAdapter()
        fragmentFavoriteBinding.recyclerView.adapter = adapter
        adapter.setOnItemClickCallback(object : FavoriteRecyclerAdapter.OnItemClickCallback{
            override fun onItemClicked(data: DataFilm) {
                val detailFavoriteIntent = Intent(activity, DetailActivity::class.java)
                detailFavoriteIntent.putExtra(DetailActivity.EXTRA_DATA, data)
                detailFavoriteIntent.putExtra(DetailActivity.EXTRA_STATE, state)
                startActivity(detailFavoriteIntent)
            }
        })

        factory = ViewModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(this, factory)[FavoriteViewModel::class.java]
        if (state != null) viewModel.getListFavorite(state!!).observe(viewLifecycleOwner, Observer { list ->
            if (list != null) setAdapter(list)
        })
    }

    private fun setAdapter(listPage: PagedList<DataFilm>){
        fragmentFavoriteBinding.favoriteBar.visibility = View.GONE
        fragmentFavoriteBinding.recyclerView.visibility = View.VISIBLE
        adapter.submitList(listPage)
        adapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        fragmentFavoriteBinding.recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = FavoriteRecyclerAdapter()
        fragmentFavoriteBinding.recyclerView.adapter = adapter
        adapter.setOnItemClickCallback(object : FavoriteRecyclerAdapter.OnItemClickCallback{
            override fun onItemClicked(data: DataFilm) {
                val detailFavoriteIntent = Intent(activity, DetailActivity::class.java)
                detailFavoriteIntent.putExtra(DetailActivity.EXTRA_DATA, data)
                detailFavoriteIntent.putExtra(DetailActivity.EXTRA_STATE, state)
                startActivity(detailFavoriteIntent)
            }
        })

        factory = ViewModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(this, factory)[FavoriteViewModel::class.java]
        if (state != null) viewModel.getListFavorite(state!!).observe(viewLifecycleOwner, Observer { list ->
            if (list != null) setAdapter(list)
        })
    }

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"
        @JvmStatic
        fun newInstance(sectionNumber: Int): FavoriteHolderFragment {
            return FavoriteHolderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}