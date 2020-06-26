package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
    ImageView bkgrdImageView;
    String videoKey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        //parse to find view obj
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvOverview = (TextView) findViewById(R.id.tvOverview);
        tvVotes = (TextView) findViewById(R.id.tvVotes);
        tvReleaseDate = (TextView) findViewById(R.id.tvReleaseDate);
        rbVoteAverage = (RatingBar) findViewById(R.id.rbVoteAverage);
        playBtnImageView = (ImageView) findViewById(R.id.playBtnImageView);
        trailerImageView = (ImageView) findViewById(R.id.trailerImageView);
        bkgrdImageView = (ImageView) findViewById(R.id.bkgrdImageView);

        //unwrap movie from intent
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for %s", movie.getTitle()));

        //set the img, title, vote count, release date, and overview
        //adds rounded corners to posters
        int radius = 30; // corner radius, higher value = more rounded
        int margin = 10; // crop margin, set to 0 for corners with no crop
        GlideApp.with(this)
                .load(movie.getBackdropPath())
                .transform(new RoundedCornersTransformation(radius, margin))
                .into(trailerImageView);
//        Glide.with(this).load(movie.getBackdropPath()).into(trailerImageView);
        Glide.with(this).load(movie.getPosterPath()).into(bkgrdImageView);

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