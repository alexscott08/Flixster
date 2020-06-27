package com.example.flixster;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Placeholder;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.adapters.GlideApp;
import com.example.flixster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import okhttp3.Headers;


public class MovieDetailsActivity extends AppCompatActivity {

    final String TAG = "MovieDetailsActivity";
    Movie movie;
    TextView tvTitle;
    TextView tvOverview;
    TextView tvVotes;
    TextView tvReleaseDate;
    RatingBar rbVoteAverage;
    ImageView trailerImageView;
    ImageView playBtnImageView;
    ProgressBar progressBar;
    String videoKey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        //custom action bar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_title);

        //parse to find view obj
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvOverview = (TextView) findViewById(R.id.tvOverview);
        tvVotes = (TextView) findViewById(R.id.tvVotes);
        tvReleaseDate = (TextView) findViewById(R.id.tvReleaseDate);
        rbVoteAverage = (RatingBar) findViewById(R.id.rbVoteAverage);
        playBtnImageView = (ImageView) findViewById(R.id.playBtnImageView);
        trailerImageView = (ImageView) findViewById(R.id.trailerImageView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //unwrap movie from intent
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for %s", movie.getTitle()));

        //set the img, title, vote count, release date, and overview
        //adds rounded corners to posters
        String imageUrl;
        //If phone is in landscape, use backdrop; else use poster image
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            imageUrl = movie.getPosterPath();
        } else {
            imageUrl = movie.getBackdropPath();
        }

        // Loads progressBar if image takes awhile to load
        progressBar.setVisibility(View.VISIBLE);

        //adds rounded corners to posters
        int radius = 30; // corner radius, higher value = more rounded
        int margin = 10; // crop margin, set to 0 for corners with no crop
        GlideApp.with(this)
                .load(imageUrl).placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        // log exception
                        Log.e("TAG", "Error loading image", e);
                        return false; // important to return false so the error placeholder can be placed
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                }).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                progressBar.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                progressBar.setVisibility(View.GONE);
                return false; // important to return false so the error placeholder can be placed
            }
        })
            .transform(new RoundedCornersTransformation(radius, margin))
                .into(trailerImageView);

        tvTitle.setText(movie.getTitle());
        tvOverview.setMovementMethod(new ScrollingMovementMethod());
        tvOverview.setText(movie.getOverview());

        int voteCount = movie.getVotes();
        //adds commas to vote count
        String voteStr = voteCount + "";
        String voteCommas = voteStr;
        if (voteStr.length() > 3) {
            int counter = 1;
            for (int i = voteStr.length() - 1; i > 0; i--) {
                if (counter % 3 == 0) {
                    voteCommas = voteCommas.substring(0, i) + "," + voteCommas.substring(i);
                }
                counter++;
            }
        }
        tvVotes.setText("Votes: " + voteCommas);

        tvReleaseDate.setText("Released: " + movie.getReleaseDate());

        //vote average is 0..10, convert to 0..5 by dividing by 2
        float voteAverage = movie.getVoteAverage().floatValue();
        rbVoteAverage.setRating(voteAverage = voteAverage > 0 ? voteAverage / 2.0f : voteAverage);

        //on click listener to launch trailer when img is clicked
        playBtnImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MovieDetailsActivity.this, MovieTrailerActivity.class);
                i.putExtra("id", videoKey);
                startActivity(i);
            }
        });
        
        //an asynchronous call to find the id of the movie's trailer
        try {
            movie.findTrailerId(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Headers headers, JSON json) {
                    Log.d(TAG, "onSuccess");
                    JSONObject jsonObject = json.jsonObject;
                    try {
                        JSONArray results = jsonObject.getJSONArray("results");
                        Log.i(TAG, "Results: " + results.toString());
                        if (results != null) {
                            videoKey = results.getJSONObject(0).getString("key");
                        }
                    } catch (JSONException e) {
                        Log.e(TAG, "Hit json exception", e);
                    }
                }

                @Override
                public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}