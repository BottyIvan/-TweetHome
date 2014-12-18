package com.botty.launcher.FragLayout;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.botty.launcher.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.pkmmte.view.CircularImageView;

public class Home extends Fragment {

    /**
     * Register your here app https://dev.twitter.com/apps/new and get your
     * consumer key and secret
     */
    static String TWITTER_CONSUMER_KEY = "yuXQup6TIua9T8w5jaPoUmcw1";
    static String TWITTER_CONSUMER_SECRET = "VN98SmuAp62eJ2foQ6OeBsLGPXGCLUYyDzrwlwOj5Zjx3s6j9t";
    // Preference Constants
    static String PREFERENCE_NAME = "twitter_oauth";
    static final String PREF_KEY_OAUTH_TOKEN = "oauth_token";
    static final String PREF_KEY_OAUTH_SECRET = "oauth_token_secret";
    static final String PREF_KEY_TWITTER_LOGIN = "isTwitterLogedIn";
    static final String TWITTER_CALLBACK_URL = "oauth://t4jsample";
    // Twitter oauth urls
    static final String URL_TWITTER_AUTH = "auth_url";
    static final String URL_TWITTER_OAUTH_VERIFIER = "oauth_verifier";
    static final String URL_TWITTER_OAUTH_TOKEN = "oauth_token";

    // Shared Preferences
    private static SharedPreferences mSharedPreferences;
    // adapter that holds tweets, obviously :)
    ArrayAdapter<JsonObject> tweetAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_home, container, false);

        // Shared Preferences
        mSharedPreferences = getActivity().getSharedPreferences(
                "#TweetHomePref", 0);

       // final CircularImageView imageView = (CircularImageView)rootView.findViewById(R.id.image);
        // Enable global Ion logging
        // Ion.getDefault(this).setLogging("ion-sample", Log.DEBUG);
        // create a tweet adapter for our list view
        tweetAdapter = new ArrayAdapter<JsonObject>(getActivity(), 0) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null)
                    convertView = getActivity().getLayoutInflater().inflate(R.layout.tweet, null);
                // we're near the end of the list adapter, so load more items
                if (position >= getCount() - 3)
                    load();
                // grab the tweet (or retweet)
                JsonObject tweet = getItem(position);
                JsonObject retweet = tweet.getAsJsonObject("retweeted_status");
                if (retweet != null)
                    tweet = retweet;
                // grab the user info... name, profile picture, tweet text
                JsonObject user = tweet.getAsJsonObject("user");
                String twitterId = user.get("screen_name").getAsString();
                // set the profile photo using Ion
                String imageUrl = user.get("profile_image_url").getAsString();
                // i hope soon i'll get the banner...
                String coverUrl = user.get("profile_image_url").getAsString();
                ImageView cover = (ImageView) convertView.findViewById(R.id.cover);
                Ion.with(cover)
                        .load(coverUrl);
                ImageView imageView = (ImageView) convertView.findViewById(R.id.image);
                // Use Ion's builder set the google_image on an ImageView from a URL
                // start with the ImageView
                Ion.with(imageView)
                        .load(imageUrl);
                // and finally, set the name and text
                TextView handle = (TextView) convertView.findViewById(R.id.handle);
                handle.setText(twitterId);
                TextView text = (TextView) convertView.findViewById(R.id.tweet);
                text.setText(tweet.get("text").getAsString());
                return convertView;
            }
        };
        // basic setup of the ListView and adapter
        ListView listView = (ListView) rootView.findViewById(R.id.tweetList);
        listView.setAdapter(tweetAdapter);
        // authenticate and do the first load
        getCredentials();

        return rootView;
    }
    // This "Future" tracks loading operations.
    // A Future is an object that manages the state of an operation
    // in progress that will have a "Future" result.
    // You can attach callbacks (setCallback) for when the result is ready,
    // or cancel() it if you no longer need the result.
    Future<JsonArray> loading;
    String accessToken;
        private void getCredentials() {
            Ion.with(this)
                    .load("https://api.twitter.com/oauth2/token")
                    // embedding twitter api key and secret is a bad idea, but this isn't a real twitter app :)
                    .basicAuthentication(TWITTER_CONSUMER_KEY, TWITTER_CONSUMER_SECRET)
                    .setBodyParameter("grant_type", "client_credentials")
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            if (e != null) {
                                Toast.makeText(getActivity(), "Error loading tweets", Toast.LENGTH_LONG).show();
                                return;
                            }
                            accessToken = result.get("access_token").getAsString();
                            load();
                        }
                    });
        }
    private void load() {
        // don't attempt to load more if a load is already in progress
        if (loading != null && !loading.isDone() && !loading.isCancelled())
            return;
        // load the tweets
        String url = "https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=Digi_advntr15th";
        if (tweetAdapter.getCount() > 0) {
            // load from the "last" id
            JsonObject last = tweetAdapter.getItem(tweetAdapter.getCount() - 1);
            url += "&max_id=" + last.get("id_str").getAsString();
        }
        // Request tweets from Twitter using Ion.
        // This is done using Ion's Fluent/Builder API.
        // This API lets you chain calls together to build
        // complex requests.
        // This request loads a URL as JsonArray and invokes
        // a callback on completion.
        loading = Ion.with(this)
                .load(url)
                .setHeader("Authorization", "Bearer " + accessToken)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                    // this is called back onto the ui thread, no Activity.runOnUiThread or Handler.post necessary.
                        if (e != null) {
                            Toast.makeText(getActivity(), "Error loading tweets", Toast.LENGTH_LONG).show();
                            return;
                        }
                        // add the tweets
                        for (int i = 0; i < result.size(); i++) {
                            tweetAdapter.add(result.get(i).getAsJsonObject());
                        }
                    }
                });
    }
}



