package com.example.flixster.adapters;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.flixster.R;
import com.example.flixster.models.Movie;

import org.parceler.Parcels;


public class MovieDetailsActivity extends AppCompatActivity {

    Movie movie;
    TextView tvTitle;
    TextView tvOverview;
    TextView tvVotes;
    TextView tvReleaseDate;
    RatingBar rbVoteAverage;


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


        //unwrap movie from intent
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for %s", movie.getTitle()));



        //set the title, vote count, release date, and overview
        tvTitle.setText(movie.getTitle());
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
    }
}