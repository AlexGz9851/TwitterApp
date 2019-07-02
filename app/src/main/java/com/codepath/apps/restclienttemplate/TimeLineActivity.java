package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.codepath.apps.restclienttemplate.models.Tweet;

import java.util.ArrayList;

public class TimeLineActivity extends AppCompatActivity {

    TweetAdapter tweetAdapter;
    TwitterClient client;
    ArrayList<Tweet> tweets ;
    RecyclerView rvTweets;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);

        client = TwitterApp.getRestClient();

        populateTimeline();

    }
}
