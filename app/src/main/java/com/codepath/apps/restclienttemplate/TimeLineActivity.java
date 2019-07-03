package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

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

    TweetAdapter tweetAdapter;

    public TwitterClient getClient() {
        return client;
    }

    TwitterClient client;
    ArrayList<Tweet> tweets ;
    RecyclerView rvTweets;

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
    private  void populateTimeline(){
        client.getHomeTimeline(1, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
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
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("TimeLineActivity","Couldn't load tweets");
            }
        });
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
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            Tweet resultTweet = Parcels.unwrap(getIntent().getParcelableExtra("tweetWritten"));
            // basically we're showing the tweet which just write. don't come from API, is what user just write.
            // the post was done, but also we want show the tweet right now.
            tweets.add(resultTweet);
            tweetAdapter.notifyItemInserted(tweets.size()-1);

        }
    }
}
