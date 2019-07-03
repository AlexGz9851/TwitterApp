package com.codepath.apps.restclienttemplate.models;

import android.text.format.DateUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

@Parcel
public class Tweet {
    public String body;
    public long uid; // database tweet id
    public String createdDate;
    public User user;
    public String relativeTime; // Time ago tweet was posted.

    // key: created_at
    public Tweet() {
    }

    // deserialize JSON
    public static Tweet fromJSON(JSONObject json) throws JSONException {
        Tweet tweet = new Tweet();

        //extract values from  JSON
        tweet.body = json.getString("text");
        tweet.uid = json.getLong("id");
        tweet.createdDate = json.getString("created_at");
        tweet.user = User.fromJSON(json.getJSONObject("user"));
        tweet.relativeTime = getRelativeTimeAgo(json.getString("created_at"));

        return tweet;

    }

    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    public static String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;

    }
}
