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

    public static final int TWEET_MAX_LENGHT=280;
    TwitterClient client;
    EditText message;
    TextView counter;
    Button postTweetBtn;
    int count;
    String actionName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        count=280;
        actionName = getIntent().getExtras().getString("action_name");

        setContentView(R.layout.activity_compose);
        client = new TwitterClient(this);
        counter=findViewById(R.id.tvCounter);
        postTweetBtn = findViewById(R.id.post_tweet);

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
                    count=TWEET_MAX_LENGHT-message.getText().length();
                    counter.setText(String.format("%d",count));

                    if(count>=0){// can't send large tweets tan the limit
                        postTweetBtn.setEnabled(true);
                        counter.setTextColor(getResources().getColor(R.color.medium_gray));
                        postTweetBtn.setBackgroundColor(getResources().getColor(R.color.twitter_blue));
                        postTweetBtn.setTextColor(getResources().getColor(R.color.white));
                    }else{
                        postTweetBtn.setEnabled(false);// can't send large tweets tan the limit
                        counter.setTextColor(getResources().getColor(R.color.medium_red));// set counter in red if tweet is large.
                        postTweetBtn.setBackgroundColor(getResources().getColor(R.color.light_gray));
                        postTweetBtn.setTextColor(getResources().getColor(R.color.background_color));
                    }


            }
        });

    }

    @Override
    public void onClick(View v){

        switch (actionName){
            case "tweet":

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

                break;


            case "comment":
                //TODO DIFFERENT ACTIONS, DIFFERENT JSON, DIFERENT URL.
                client.comment();
                break;
            default:
                //TODO DIFFERENT ACTIONS, DIFFERENT JSON, DIFERENT URL.
                Intent i = new Intent();
                break;
        }

    }
}
