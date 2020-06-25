package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
    ImageView trailerImageView;


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
        trailerImageView = (ImageView) findViewById(R.id.trailerImageView);


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
        trailerImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MovieDetailsActivity.this, MovieTrailerActivity.class);
                i.putExtra("onClick", movie.getId() + "");
                startActivity(i);
            }
        });
    }

//    public void launchMovieTrailerActivity(View view) {
//        Intent i = new Intent();
//        i.putExtra("onClick", movie.getId() + "");
//        int position = getAdapterPosition();
//
//        //only if position is valid
//        if (position != RecyclerView.NO_POSITION) {
//            Movie movie = movies.get(position);
//            //create intent for new activity
//            Intent intent = new Intent(context, MovieDetailsActivity.class);
//            //serialize movie w/ parceler, use short name for key
//            intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));
//            //show activity
//            context.startActivity(intent);
//        }
//
//    }
}