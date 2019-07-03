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

import butterknife.BindView;
import butterknife.ButterKnife;

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
        viewHolder.tvUsername.setText(tweet.user.name);
        viewHolder.tvBody.setText(tweet.body);
        viewHolder.tvRelativeTime.setText(tweet.relativeTime);
        Glide.with(context).load(tweet.user.profileImageUrl).into(viewHolder.ivProfileImage);



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
        public @BindView(R.id.tvUsername) TextView tvUsername ;
        public @BindView(R.id.tvBody) TextView tvBody;
        public @BindView(R.id.tvRelativeTime) TextView tvRelativeTime;
        public @BindView(R.id.ivProfileImage) ImageView ivProfileImage ;

        public ViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
