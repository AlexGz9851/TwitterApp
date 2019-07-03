package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class Tweet {
    public String body;
    public long uid; // database tweet id
    public String createdDate;
    public User user;

    public Tweet(){}
    // deserialize JSON
    public static Tweet fromJSON(JSONObject json) throws JSONException {
        Tweet tweet = new Tweet();

        //extract values from  JSON
        tweet.body = json.getString("text");
        tweet.uid = json.getLong("id");
        tweet.createdDate = json.getString("created_at");
        tweet.user = User.fromJSON(json.getJSONObject("user"));

        return  tweet;

    }
}
