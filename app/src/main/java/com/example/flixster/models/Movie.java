package com.example.flixster.models;

import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.*;
import org.parceler.Parcel;

import java.util.*;

import okhttp3.Headers;

@Parcel
public class Movie {

    //all fields must be public for parceler
    public static final String TRAILER_URL = "https://api.themoviedb.org/3/movie/" + "{movie_id}" +
            "?api_key=AIzaSyCYmklfPJL7nL08MIGyAEUjUldqWekQ77I";
    public static final String TAG = "Movie";
    String backdropPath;
    String posterPath;
    String title;
    String overview;
    int votes;
    String releaseDate;
    Double voteAverage;
    int id;

    public Movie() {
    }

    public Movie(JSONObject jsonObject) throws JSONException {
        backdropPath = jsonObject.getString("backdrop_path");
        posterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        votes = jsonObject.getInt("vote_count");
        releaseDate = jsonObject.getString("release_date");
        voteAverage = jsonObject.getDouble("vote_average");
        id = jsonObject.getInt("id");
    }

    public static List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < movieJsonArray.length(); i++) {
            movies.add(new Movie(movieJsonArray.getJSONObject(i)));
        }
        return movies;
    }

    public static List<String> fromJsonArrayTrailer(JSONArray trailerJsonArray) throws JSONException {
        List<String> trailers = new ArrayList<>();
        for (int i = 0; i < trailerJsonArray.length(); i++) {
            trailers.add(trailerJsonArray.getJSONObject(i).toString());
        }
        return trailers;
    }


    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", backdropPath);
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public int getVotes() {
        return votes;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public int getId() {
        return id;
    }

}
