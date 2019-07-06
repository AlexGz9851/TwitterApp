package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.util.List;

public class TweetAdapter  extends RecyclerView.Adapter<TweetAdapter.ViewHolder>{
    private List<Tweet> tweets;
    Context context;
    // pass Tweets array  to adapter in constructor
    public TweetAdapter (List<Tweet> tweets){
        this.tweets = tweets;
    }
    // for each row, inflate the layout and cache references into viewholder

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View tweetView = inflater.inflate(R.layout.item_twitter, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(tweetView);
        return viewHolder;
    }

    // bind the values based on the position of the element
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        // get data according position
        Tweet tweet = tweets.get(i);

        // populate the views according data
        viewHolder.tvUsername.setText(tweet.getUser().name);
        viewHolder.tvBody.setText(tweet.getBody());
        viewHolder.tvRelativeTime.setText(tweet.getRelativeTime());
        viewHolder.tvLikes.setText(numberShortFormat(tweet.getLikes()));
        viewHolder.tvRetweets.setText(numberShortFormat(tweet.getRetweets()));
        Glide.with(context)
                .load(tweet.getUser().profileImageUrl)
                .placeholder(R.drawable.user_placeholder)
                .into(viewHolder.ivProfileImage);
                // I CANT USE .apply(RequestOptions.circleCropTransform()) I DONT KNOW WHY!!!
        if(tweet.hasEntities){
            String entURL = tweet.entity.loadURL;

            Glide.with(context).load(entURL).into(viewHolder.tweet_entity);
        }else{
            viewHolder.tweet_entity.setVisibility(View.GONE);
        }

    }

    // convert large numbers into suffix notation. 23043 --> 23K
    public String numberShortFormat(int x){
        if(x<1000){
            return String.format("%d", x);// regular case. no suffix
        }else if(x>=1000 && x<1000000){
            float d= x/1000.0f;
            return String.format("%dK", Math.round(d));
        }else{
            float d= x/1000000.0f;
            return String.format("%dM", Math.round(d));
        }
    }


    // Clean all elements of the recycler
    public void clear() {
        tweets.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Tweet> list) {
        tweets.addAll(list);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount(){
        return tweets.size();
    }

    // create ViewHolder class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvUsername,
                tvBody,
                tvRelativeTime,
                tvComments,
                tvLikes,
                tvRetweets;
        public ImageView ivProfileImage , tweet_entity;

        public ViewHolder(View itemView){
            super(itemView);

            ivProfileImage = (ImageView)itemView.findViewById(R.id.ivProfileImage);
            tvBody = (TextView)itemView.findViewById(R.id.tvBody);
            tvUsername =(TextView) itemView.findViewById(R.id.tvUsername);
            tvRelativeTime = (TextView) itemView.findViewById(R.id.tvRelativeTime);
            tvComments = (TextView) itemView.findViewById(R.id.tvComments);
            tvLikes = (TextView) itemView.findViewById(R.id.tvLikes);
            tvRetweets = (TextView) itemView.findViewById(R.id.tvRetweets);
            tweet_entity = (ImageView) itemView.findViewById(R.id.tweet_entity);

        }
    }
}
