package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int TWITTER_LENGHT=280;// tw characterers lenght
    TwitterClient client;
    EditText message;
    TextView counter;
    Button postTweetBtn;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        count=280;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        client = new TwitterClient(this);
        counter=findViewById(R.id.tvCounter);
        postTweetBtn = findViewById(R.id.postTweet);

        counter.setText(String.format("%d",count));
        message = (EditText) findViewById(R.id.messageTweet);
        message.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                    count=TWITTER_LENGHT-message.getText().length();
                    counter.setText(String.format("%d",count));
                    postTweetBtn.setEnabled(count>=0);// can't send large tweets tan the limit
            }
        });

    }

    @Override
    public void onClick(View v){

        client.postTweet(message.getText().toString(), new JsonHttpResponseHandler(){
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("ComposeActivity", "onSuccess(int, Header[], JSONObject) was not overriden, but callback was received");
                try {
                    Tweet myTweet = Tweet.fromJSON(response);
                    Intent i = new Intent();
                    i.putExtra("tweet_post", Parcels.wrap(myTweet));
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
