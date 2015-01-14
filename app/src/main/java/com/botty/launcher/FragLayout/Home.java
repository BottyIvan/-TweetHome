package com.botty.launcher.FragLayout;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.botty.launcher.R;

import java.util.ArrayList;
import java.util.List;

import twitter4j.JSONArray;
import twitter4j.JSONObject;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.json.DataObjectFactory;


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
    List<Status> statuses = new ArrayList<Status>();


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_home, container, false);

        // Shared Preferences
        mSharedPreferences = getActivity().getSharedPreferences(
                "#TweetHomePref", 0);
        Button mbtn = (Button) rootView.findViewById(R.id.button_load);
        mbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ConfigurationBuilder builder = new ConfigurationBuilder();

                    // GET THE CONSUMER KEY AND SECRET KEY FROM THE STRINGS XML
                    String CONSUMER_KEY = TWITTER_CONSUMER_KEY;
                    String CONSUMER_SECRET = TWITTER_CONSUMER_SECRET;

                    // TWITTER ACCESS TOKEN
                    String twit_access_token = mSharedPreferences.getString(PREF_KEY_OAUTH_TOKEN, null);

                    // TWITTER ACCESS TOKEN SECRET
                    String twit_access_token_secret = mSharedPreferences.getString(PREF_KEY_OAUTH_SECRET, null);

                    builder.setOAuthConsumerKey(CONSUMER_KEY);
                    builder.setOAuthConsumerSecret(CONSUMER_SECRET);
                    builder.setOAuthAccessToken(twit_access_token);
                    builder.setOAuthAccessTokenSecret(twit_access_token_secret);
                    builder.setJSONStoreEnabled(true);
                    builder.setIncludeEntitiesEnabled(true);
                    builder.setIncludeMyRetweetEnabled(true);
                    builder.setJSONStoreEnabled(true);
                    builder.setIncludeMyRetweetEnabled(true);

                    AccessToken accessToken = new AccessToken(twit_access_token, twit_access_token_secret);
                    Twitter twitter = new TwitterFactory(builder.build()).getInstance(accessToken);

                    statuses = twitter.getHomeTimeline();

                    try {
                        String strInitialDataSet = DataObjectFactory.getRawJSON(statuses);
                        JSONArray JATweets = new JSONArray(strInitialDataSet);
                        String tweets = "";
                        for (int i = 0; i < JATweets.length(); i++) {
                            JSONObject JOTweets = JATweets.getJSONObject(i);
                            Log.e("TWEETS", JOTweets.toString());
                            int j = i+1;
                            tweets += "Date:" + JOTweets.getString("created_at") + "\n";
                            tweets += "Post:" + JOTweets.getString("text") + "\n\n";
                            Toast.makeText(getActivity(),tweets,Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        });
        return rootView;
    }
}



