package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity implements View.OnClickListener {

    TwitterClient client;
    EditText message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        client = new TwitterClient(this);
        message = (EditText) findViewById(R.id.messageTweet);

    }

    @Override
    public void onClick(View v){

        client.postTweet(message.getText().toString(), new JsonHttpResponseHandler(){
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("ComposeActivity", "onSuccess(int, Header[], JSONObject) was not overriden, but callback was received");
                try {
                    Tweet myTweet = Tweet.fromJSON(response);
                    Intent i = new Intent();
                    i.putExtra("tweetWritten", Parcels.wrap(myTweet));
                    setResult(RESULT_OK,i);
                    finish();

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }

            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("ComposeActivity", "onFailure(int, Header[], Throwable, JSONObject) was not overriden, but callback was received", throwable);
            }
        });
    }
}
