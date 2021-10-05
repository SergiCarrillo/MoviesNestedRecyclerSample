package com.sergi.nestedrecyclersample.app.ui.main
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sergi.nestedrecyclersample.R
import com.sergi.nestedrecyclersample.databinding.ViewHorizontalMovieBinding
import com.sergi.nestedrecyclersample.domain.Movie
import com.sergi.nestedrecyclersample.utils.basicDiffUtil
import com.sergi.nestedrecyclersample.utils.formatDate
import com.sergi.nestedrecyclersample.utils.inflate
import com.sergi.nestedrecyclersample.utils.loadUrl


class MoviesHorizontalAdapter(private val listener: (Movie) -> Unit) :
    RecyclerView.Adapter<MoviesHorizontalAdapter.ViewHolder>() {

    var movies: List<Movie> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.id == new.id }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.view_horizontal_movie, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.render(movie)
        holder.itemView.setOnClickListener { listener(movie) }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ViewHorizontalMovieBinding.bind(view)

        fun render(movie: Movie) = with(binding) {
            renderTitle(movie.title)
            renderCover(movie.posterPath)
            renderDate(movie.releaseDate)
        }

        private fun renderTitle(title : String) = with(binding) {
            tvMovie.text = title
        }

        private fun renderCover(cover : String) = with(binding) {
            ivMovie.loadUrl("https://image.tmdb.org/t/p/w185/${cover}")
        }

        private fun renderDate(date : String) = with(binding) {
            tvDate.text =  "Estreno\n${date.formatDate("dd/MM/yyyy")}"
        }

    }
}