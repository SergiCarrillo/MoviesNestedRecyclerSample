package com.sergi.nestedrecyclersample.app.ui.main

import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sergi.nestedrecyclersample.R
import com.sergi.nestedrecyclersample.databinding.ItemMovieSectionBinding
import com.sergi.nestedrecyclersample.domain.Movie
import com.sergi.nestedrecyclersample.domain.MovieSection
import com.sergi.nestedrecyclersample.utils.basicDiffUtil

class MoviesSectionAdapter(private val listener: (Movie) -> Unit) : RecyclerView.Adapter<MoviesSectionAdapter.ViewHolder>() {

    private var onRenderMovieSection: ((ViewHolder) -> Unit)? = null

    private val scrollStates: MutableMap<String, Parcelable?> = mutableMapOf()

    var sections: List<MovieSection> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.id == new.id },
        areContentsTheSame = { old, new -> old == new }
    )

    init {
        onRenderMovieSection = { onRenderMovieSection(it) }
    }

    private fun getSectionID(position: Int): String {
        return sections[position].id
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        //save horizontal scroll state
        val key = getSectionID(holder.layoutPosition)
        scrollStates[key] = holder.itemView.findViewById<RecyclerView>(R.id.rvSection).layoutManager?.onSaveInstanceState()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val view = ItemMovieSectionBinding.inflate(inflate)
        return ViewHolder(view.root, listener, onRenderMovieSection)
    }

    private fun onRenderMovieSection(holder: ViewHolder) {
        //restore horizontal scroll state
        val key = getSectionID(holder.layoutPosition)
        val state = scrollStates[key]
        holder.restoreScrollState(state)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.render(sections[position])
    }

    override fun getItemCount(): Int = sections.size


    class ViewHolder(view: View, private val listener: (Movie) -> Unit,
        private val onRenderPublication: ((ViewHolder) -> Unit)?)
        : RecyclerView.ViewHolder(view) {

        private val binding = ItemMovieSectionBinding.bind(view)

        fun render(movieSection: MovieSection) {
            renderTitle(movieSection.title)
            renderMovies(movieSection.movies, movieSection.isHorizontal)
        }

        private fun renderTitle(title: String?) = with(binding) {
            tvSection.text = title
        }

        private fun renderMovies(movies: List<Movie>, isHorizontal : Boolean) = with(binding) {
            val viewPool = RecyclerView.RecycledViewPool()
            val childLayoutManager = createLayoutManager(isHorizontal)
            val childAdapter = if (isHorizontal) { MoviesHorizontalAdapter(listener) }
            else { MoviesVerticalAdapter(listener) }
            rvSection.run {
                setRecycledViewPool(viewPool)
                setHasFixedSize(true)
                layoutManager = childLayoutManager
                adapter = childAdapter

                when (childAdapter) {
                    is MoviesHorizontalAdapter -> {
                        childAdapter.movies = movies
                    }
                    is MoviesVerticalAdapter -> {
                        childAdapter.movies = movies
                    }
                }

                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        Log.d("OnScrolled", "dx: $dx dy: $dy")
                    }
                })
            }
            onRenderPublication?.invoke(this@ViewHolder)
        }

        private fun createLayoutManager(horizontal : Boolean) : RecyclerView.LayoutManager  {
            val layoutManager = if (horizontal) {
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            }
            else {
                GridLayoutManager(itemView.context, 2)
            }

            layoutManager.initialPrefetchItemCount = 4

            return layoutManager
        }

        fun restoreScrollState(state: Parcelable?) = with(binding) {
            state?.let {
                rvSection.layoutManager?.onRestoreInstanceState(state)
            } ?: run {
                rvSection.layoutManager?.scrollToPosition(0)
            }
        }

    }

}

