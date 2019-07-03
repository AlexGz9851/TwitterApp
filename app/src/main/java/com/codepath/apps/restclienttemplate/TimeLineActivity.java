package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TimeLineActivity extends AppCompatActivity  {


    // REQUEST_CODE can be any value we like, used to determine the result type later
    private final int REQUEST_CODE = 20;

    private SwipeRefreshLayout swipeContainer;
    TweetAdapter tweetAdapter;
    TwitterClient client;
    ArrayList<Tweet> tweets ;
    RecyclerView rvTweets;
    MenuItem miActionProgressItem;


    public TwitterClient getClient() {
        return client;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.time_line, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("TimeLineActivity", "CreatedTimeLine");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);

        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                fetchTimelineAsync(0);
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        client = TwitterApp.getRestClient(this);
        Log.d("TimeLineActivity", "client created"+client.toString());
        //find the recycler view
        rvTweets = (RecyclerView) findViewById(R.id.rvTweet);
        // init the array list (data source)
        tweets = new ArrayList<Tweet>();
        // construct the adapter from this datasource
        tweetAdapter= new TweetAdapter(tweets);
        // recyclerView setup ( layout manager, use adapter)
        rvTweets.setLayoutManager(new LinearLayoutManager(this));
        // set the adapter
        rvTweets.setAdapter(tweetAdapter);
        populateTimeline();

    }

    public void fetchTimelineAsync(int page) {
        showProgressBar();
        // Send the network request to fetch the updated data
        // `client` here is an instance of Android Async HTTP
        // getHomeTimeline is an example endpoint.
        client.getHomeTimeline(1,new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                // Remember to CLEAR OUT old items before appending in the new ones
                tweetAdapter.clear();
                // ...the data has come back, add new items to your adapter...
                parseTweetsfromJSONArray(response);
                // Now we call setRefreshing(false) to signal refresh has finished
                swipeContainer.setRefreshing(false);
            }


            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("DEBUG", "Fetch timeline error: " + throwable.toString());

            }
        });
        hideProgressBar();
    }


    private  void populateTimeline(){
        client.getHomeTimeline(1, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                parseTweetsfromJSONArray(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("TimeLineActivity","Couldn't load tweets");
            }
        });
    }

    public void parseTweetsfromJSONArray(JSONArray response){
        //
        Log.d("TimeLineActivity", "Succeded");
        // iterate through the JSON array
        for(int i=0;i<response.length();i++){
            // for each entry. deserializae the JSON obj
            // convert each object to a Tweet model
            // add that tweet model to our data source
            // notify adapter that we're added an item
            try {
                Tweet tweet = Tweet.fromJSON(response.getJSONObject(i));
                tweets.add(tweet);
                tweetAdapter.notifyItemInserted(tweets.size()-1);
            }catch (JSONException e){
                e.printStackTrace();
                Log.d("TimeLineActivity", "Coudn't get tw list");
            }


        }Log.d("TimeLineActivity", "'tw list loaded");
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.make_a_tweet: {
                Intent i = new Intent(this, ComposeActivity.class );

                startActivityForResult(i, REQUEST_CODE);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Get results from child activity
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE ) {
            Intent i = getIntent();
            Tweet resultTweet =(Tweet) Parcels.unwrap(i.getParcelableExtra("tweet_post"));
            // basically we're showing the tweet which just write. don't come from API, is what user just write.
            // the post was done, but also we want show the tweet right now
            tweets.add(0, resultTweet);
            tweetAdapter.notifyItemInserted(0);
            rvTweets.scrollToPosition(0);

        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Store instance of the menu item containing progress
        miActionProgressItem = menu.findItem(R.id.miActionProgress);
        // Extract the action-view from the menu item
        ProgressBar v =  (ProgressBar) MenuItemCompat.getActionView(miActionProgressItem);
        // Return to finish
        return super.onPrepareOptionsMenu(menu);
    }

    public void showProgressBar() {
        // Show progress item
        miActionProgressItem.setVisible(true);
    }

    public void hideProgressBar() {
        // Hide progress item
        miActionProgressItem.setVisible(false);
    }
}
