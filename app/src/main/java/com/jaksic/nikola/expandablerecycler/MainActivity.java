package com.jaksic.nikola.expandablerecycler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.transition.TransitionManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Movie> movieList = new ArrayList<>();

        movieList.add(new Movie("Schindler's List", "Biography, Drama, History", 1993, "Shimmer effect was created by Facebook to indicate a loading status, so instead of using ProgressBar or usual loader use Shimmer for a better design and user interface. They also open-sourced a library called"));
        /*movieList.add(new Movie("Pulp Fiction", "Crime, Drama", 1994,""));
        movieList.add(new Movie("No Country for Old Men", "Crime, Drama, Thriller", 2007,""));
        movieList.add(new Movie("Léon: The Professional", "Crime, Drama, Thriller", 1994,""));
        movieList.add(new Movie("Fight Club", "Drama", 1999,""));
        movieList.add(new Movie("Forrest Gump", "Drama, Romance", 1994,""));
        movieList.add(new Movie("The Shawshank Redemption", "Crime, Drama", 1994,""));
        movieList.add(new Movie("The Godfather", "Crime, Drama", 1972,""));
        movieList.add(new Movie("A Beautiful Mind", "Biography, Drama", 2001,""));
        movieList.add(new Movie("Good Will Hunting", "Drama", 1997,""));*/

        RecAdapter adapter = new RecAdapter(movieList, this);

        RecyclerView recyclerView = findViewById(R.id.recview);

        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);


    }
}