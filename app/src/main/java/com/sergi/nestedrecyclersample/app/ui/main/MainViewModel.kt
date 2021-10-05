package com.sergi.nestedrecyclersample.app.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.sergi.nestedrecyclersample.app.ui.common.ScopedViewModel
import com.sergi.nestedrecyclersample.domain.Movie
import com.sergi.nestedrecyclersample.domain.MovieSection
import com.sergi.nestedrecyclersample.usecase.GetPopularMovies
import com.sergi.nestedrecyclersample.usecase.GetUpcomingMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val state: SavedStateHandle,
                                        private val getPopularMovies: GetPopularMovies,
                                        private val getUpcomingMovies: GetUpcomingMovies) : ScopedViewModel() {

    companion object {
        const val SECTION_KEY = "sectionsKey"
    }

    private val _sections: MutableLiveData<List<MovieSection>> =
        MutableLiveData(state[SECTION_KEY] ?: mutableListOf())

    val sections: LiveData<List<MovieSection>> get() = _sections

    private val _model = MutableLiveData<UiModel>()

    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) {
                refresh()
            }
            return _model
        }

    init {
        initScope()
    }

    private fun refresh() {
        _model.value = UiModel.ShowLoading
        _model.value = UiModel.RequestMovies
    }

    fun onGetPopularMovies() {
        _model.value = UiModel.ShowLoading
        launch {
            _model.value = UiModel.HideLoading
            _model.value = UiModel.ShowMovies(getPopularMovies.invoke(1), "Películas Populares", false)
        }
    }

    fun onGetUpComingMovies() {
        _model.value = UiModel.ShowLoading
        launch {
            _model.value = UiModel.HideLoading
            _model.value = UiModel.ShowMovies(getUpcomingMovies.invoke(1), "Películas Proximas", true)
        }
    }

    fun saveIntanceSections(sections : List<MovieSection>) {
        state.set(SECTION_KEY, sections)
    }

    fun onMovieClicked(movie: Movie) {
        //_model.value = UiModel.Navigation(movie)
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }

}