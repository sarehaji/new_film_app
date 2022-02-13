package com.example.jetpacksub3.ui

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.jetpacksub3.R
import com.example.jetpacksub3.data.source.DataDetail
import com.example.jetpacksub3.data.source.DataFilm
import com.example.jetpacksub3.databinding.ActivityDetailBinding
import com.example.jetpacksub3.utils.EspressoIdlingResource
import com.example.jetpacksub3.viewmodel.DetailViewModel
import com.example.jetpacksub3.viewmodel.ViewModelFactory

class DetailActivity : AppCompatActivity() {

    private lateinit var activityDetailBinding : ActivityDetailBinding
    companion object {
        const val EXTRA_DATA = "extra_data"
        const val EXTRA_STATE = "extra_state"
    }

    private lateinit var viewModel: DetailViewModel
    private lateinit var factory: ViewModelFactory
    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(activityDetailBinding.root)

        val film = intent.getParcelableExtra<DataFilm>(EXTRA_DATA) as DataFilm
        setSupportActionBar(activityDetailBinding.toolbar)
        val state = intent.getIntExtra(EXTRA_STATE, 0)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.logo_back)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        activityDetailBinding.collapsingToolBar.setExpandedTitleColor(Color.WHITE)
        activityDetailBinding.collapsingToolBar.setCollapsedTitleTextColor(Color.WHITE)

        factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]
        viewModel.getFavorite(film.id, state).observe(this, Observer { film ->
            if (film != null && state == 1){
                setFavorite(true)
                loadMovie(film)
                loadImage(film.imagePath as String)
            }
            else if (film != null && state == 2){
                setFavorite(true)
                loadShow(film)
                loadImage(film.imagePath as String)
            } else setFavorite(false)
        })

        viewModel.getDetailInformation(film.id, state).observe(this, Observer { details ->
            if(details != null){
                if (state == 1){
                    if (!isFavorite){
                        loadMovie(film)
                    }
                    loadDetails(details, state)
                }
                else {
                    if (!isFavorite){
                        loadShow(film)
                    }
                    loadDetails(details, state)
                }
                loadImage(film.imagePath as String)
                showUI()
            }
        })

        activityDetailBinding.favButton.setOnClickListener {
            if (!isFavorite) {
                viewModel.insertFav(film, state)
                setFavorite(true)
            }
            else {
                viewModel.deleteFav(film, state)
                setFavorite(false)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    private fun setFavorite(state: Boolean){
        if (state){
            isFavorite = true
            activityDetailBinding.favButton.setImageResource(R.drawable.logo_favorite)
        }
        else {
            isFavorite = false
            activityDetailBinding.favButton.setImageResource(R.drawable.logo_not_favorite)
        }
    }

    private fun loadImage(imagePath: String){
        Glide.with(this)
            .load(imagePath)
            .into(activityDetailBinding.filmImage)
    }

    private fun showUI(){
        activityDetailBinding.detailBar.visibility = View.GONE
        activityDetailBinding.detailView.visibility = View.VISIBLE
        activityDetailBinding.filmImage.visibility = View.VISIBLE
    }

    private fun loadMovie(movie: DataFilm){
        EspressoIdlingResource.increment()
        activityDetailBinding.detailTitle.text = movie.title
        activityDetailBinding.detailVoteAverage.text = movie.voteAverage.toString()
        activityDetailBinding.detailRelease.text = movie.releaseDate
        activityDetailBinding.detailDescription.text = movie.overView
        activityDetailBinding.detailPopularity.text = movie.popularity.toString()
        EspressoIdlingResource.decrement()
    }

    private fun loadShow(tvShow: DataFilm){
        EspressoIdlingResource.increment()
        activityDetailBinding.detailTitle.text = tvShow.title
        activityDetailBinding.detailVoteAverage.text = tvShow.voteAverage.toString()
        activityDetailBinding.detailRelease.text = tvShow.releaseDate
        activityDetailBinding.detailDescription.text = tvShow.overView
        activityDetailBinding.detailPopularity.text = tvShow.popularity.toString()
        EspressoIdlingResource.decrement()
    }

    private fun loadDetails(dataDetail: DataDetail, state: Int){
        if (state == 1){
            EspressoIdlingResource.increment()
            activityDetailBinding.detailTagLine.text = dataDetail.tagLine
            activityDetailBinding.detailRuntime.text = dataDetail.runTime.toString()
            activityDetailBinding.detailGenre.text = dataDetail.genres.toString()
            EspressoIdlingResource.decrement()
        }
        else{
            EspressoIdlingResource.increment()
            activityDetailBinding.detailTagLine.visibility = View.GONE
            activityDetailBinding.detailGenre.text = dataDetail.genres.toString()
            activityDetailBinding.detailRuntime.text = dataDetail.runTimes.toString()
            EspressoIdlingResource.decrement()
        }
    }
}