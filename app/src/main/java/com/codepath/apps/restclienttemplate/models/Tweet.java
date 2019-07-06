package com.codepath.apps.restclienttemplate.models;

import android.text.format.DateUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

@Parcel
public class Tweet {
    private String body;
    private int retweets;
    private int likes;
    private long uid; // database tweet id
    private String createdDate;
    private User user;
    private String relativeTime; // Time ago tweet was posted.

    public Entity entity;
    public boolean hasEntities;

    // key: created_at
    public Tweet() {}

    // deserialize JSON
    public static Tweet fromJSON(JSONObject json) throws JSONException {
        Tweet tweet = new Tweet();

        //extract values from  JSON
        tweet.body = json.getString("text");
        tweet.uid = json.getLong("id");
        tweet.createdDate = json.getString("created_at");
        tweet.user = User.fromJSON(json.getJSONObject("user"));
        tweet.relativeTime = getRelativeTimeAgo(json.getString("created_at"));
        tweet.likes = json.getInt("favorite_count");
        tweet.retweets = json.getInt("retweet_count");

        JSONObject ent = json.getJSONObject("entities");
        if(ent.has("media")){
            JSONArray mediaEndpoint = ent.getJSONArray("media");
            if(mediaEndpoint!=null && mediaEndpoint.length()!=0){
                tweet.entity = Entity.fromJSON(json.getJSONObject("entities"));
                tweet.hasEntities = true;

            }else{
                tweet.hasEntities = false;
            }
        }
        //tweet.entity = Entity.fromJSON(json);

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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getRetweets() {
        return retweets;
    }

    public void setRetweets(int retweets) {
        this.retweets = retweets;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRelativeTime() {
        return relativeTime;
    }

    public void setRelativeTime(String relativeTime) {
        this.relativeTime = relativeTime;
    }
}
