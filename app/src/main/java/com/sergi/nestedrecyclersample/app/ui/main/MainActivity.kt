package com.sergi.nestedrecyclersample.app.ui.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sergi.nestedrecyclersample.databinding.ActivityMainBinding
import com.sergi.nestedrecyclersample.domain.MovieSection
import com.sergi.nestedrecyclersample.utils.enforceSingleScrollDirection
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sectionAdapter: MoviesSectionAdapter

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val concatAdapter = ConcatAdapter()
        sectionAdapter = MoviesSectionAdapter(viewModel::onMovieClicked)
        concatAdapter.addAdapter(sectionAdapter)
        val linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.recyclerview.run {
            layoutManager = linearLayoutManager
            this.adapter = concatAdapter
            enforceSingleScrollDirection()
        }
        viewModel.model.observe(this, Observer(::updateUi))
    }

    private fun updateUi(model: UiModel) {

        when (model) {
            UiModel.ShowLoading -> binding.progress.visibility =  View.VISIBLE
            UiModel.HideLoading -> binding.progress.visibility =  View.GONE
            is UiModel.ShowMovies -> {
                val sections = sectionAdapter.sections.toMutableList()
                val section = sections.find { it.title == model.section }
                section?.copy(movies = model.movies) ?: run {
                    val sec = MovieSection(title = model.section, movies = model.movies, isHorizontal = model.isHorizontal)
                    if (model.isHorizontal) {
                        sections.add(0, sec)
                    }
                    else {
                        sections.add(sec)
                    }
                }
                sectionAdapter.sections = sections
                viewModel.saveIntanceSections(sections)
            }
            UiModel.RequestMovies -> {
                viewModel.onGetUpComingMovies()
                viewModel.onGetPopularMovies()
            }
        }
    }

}