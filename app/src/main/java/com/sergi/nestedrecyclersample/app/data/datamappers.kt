package com.sergi.nestedrecyclersample.app.data

import com.sergi.nestedrecyclersample.domain.Movie
import com.sergi.nestedrecyclersample.app.data.server.Movie as ServerMovie

fun ServerMovie.toDomainMovie(): Movie =
    Movie(
        id,
        title,
        overview,
        releaseDate,
        posterPath,
        backdropPath ?: posterPath,
        originalLanguage,
        originalTitle,
        popularity,
        voteAverage,
        false
    )
