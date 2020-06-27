package com.example.flixster.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.flixster.MovieDetailsActivity;
import com.example.flixster.R;
import com.example.flixster.models.Movie;

import org.parceler.Parcels;

import java.util.*;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    //Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder");
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    //Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("MovieAdapter", "onBindViewHolder " + position);
        //Get the movie at the passed in position
        Movie movie = movies.get(position);
        //Bind the movie data into the vH
        holder.bind(movie);
    }

    //Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;
        ImageButton nextBtn;
        TextView tvDate;
        ProgressBar progressBar;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvOverview = (TextView) itemView.findViewById(R.id.tvOverview);
            ivPoster = (ImageView) itemView.findViewById(R.id.ivPoster);
            nextBtn = (ImageButton) itemView.findViewById(R.id.nextBtn);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
            //only goes to next screen when arrow button is clicked
            nextBtn.setOnClickListener(this);
        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            ;
            tvOverview.setText(movie.getOverview());
            tvDate.setText("Released: " + movie.getReleaseDate());

            /** This block of code adds placeholders and progress bars while image loads/ in case
             * image doesn't load. Can likely be cleaned up.
             **/

            String imageUrl;
            //adds rounded corners to posters
            int radius = 30; // corner radius, higher value = more rounded
            int margin = 10; // crop margin, set to 0 for corners with no crop
            progressBar.setVisibility(View.VISIBLE);
            //If phone is in landscape, use backdrop; else use poster image
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                imageUrl = movie.getBackdropPath();
                GlideApp.with(context)
                        .load(imageUrl).placeholder(R.drawable.flicks_backdrop_placeholder)
                        .error(R.drawable.flicks_backdrop_placeholder)
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                        Target<Drawable> target, boolean isFirstResource) {
                                // log exception
                                Log.e("TAG", "Error loading image", e);
                                return false; // important to return false so the error placeholder can be placed
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model,
                                                           Target<Drawable> target, DataSource dataSource,
                                                           boolean isFirstResource) {
                                return false;
                            }
                        }).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                Target<Drawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target,
                                                   DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false; // important to return false so the error placeholder can be placed
                    }
                })
                        .transform(new RoundedCornersTransformation(radius, margin))
                        .into(ivPoster);

            } else {
                imageUrl = movie.getPosterPath();
                GlideApp.with(context)
                        .load(imageUrl).placeholder(R.drawable.flicks_movie_placeholder)
                        .error(R.drawable.flicks_movie_placeholder)
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                        Target<Drawable> target, boolean isFirstResource) {
                                // log exception
                                Log.e("TAG", "Error loading image", e);
                                return false; // important to return false so the error placeholder can be placed
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model,
                                                           Target<Drawable> target, DataSource dataSource,
                                                           boolean isFirstResource) {
                                return false;
                            }
                        }).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                Target<Drawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target,
                                                   DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false; // important to return false so the error placeholder can be placed
                    }
                })
                        .transform(new RoundedCornersTransformation(radius, margin))
                        .into(ivPoster);
            }
        }

        @Override
        public void onClick(View view) {
            //get position
            int position = getAdapterPosition();

            //only if position is valid
            if (position != RecyclerView.NO_POSITION) {
                Movie movie = movies.get(position);
                //create intent for new activity
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                //serialize movie w/ parceler, use short name for key
                intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));
                //show activity
                context.startActivity(intent);
            }
        }
    }
}
