package com.sergi.nestedrecyclersample.app.ui.main

import com.sergi.nestedrecyclersample.domain.Movie

sealed class UiModel {
        object ShowLoading : UiModel()
        object HideLoading : UiModel()
        class ShowMovies(val movies: List<Movie>, val section: String, val isHorizontal: Boolean) : UiModel()
        //class Navigation(val movie: Movie) : UiModel()
        object RequestMovies : UiModel()
    }