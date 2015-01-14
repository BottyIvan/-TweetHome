package com.botty.launcher.Twitter;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.botty.launcher.R;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

public class PostUpdate extends ActionBarActivity implements View.OnClickListener {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_CONSUMER_KEY = "yuXQup6TIua9T8w5jaPoUmcw1";
    private static final String TWITTER_CONSUMER_SECRET = "VN98SmuAp62eJ2foQ6OeBsLGPXGCLUYyDzrwlwOj5Zjx3s6j9t";


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

    ImageButton mNewTweet;
    Button btnUpdateStatus;
    EditText txtUpdate;
    ProgressDialog pDialog;
    // Twitter
    private static Twitter twitter;
    private static RequestToken requestToken;
    // Shared Preferences
    private static SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_update);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Shared Preferences
        mSharedPreferences = getApplicationContext().getSharedPreferences(
                "#TweetHomePref", 0);

        btnUpdateStatus = (Button) findViewById(R.id.btnUpdateStatus);
        txtUpdate = (EditText) findViewById(R.id.txtUpdateStatus);
        btnUpdateStatus.setOnClickListener(this);

    }


    /**
     * Function to update status
     * */
    class updateTwitterStatus extends AsyncTask<String, String, String> {
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getApplicationContext());
            pDialog.setMessage("Update in corso...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
        /**
         * getting Places JSON
         * */
        protected String doInBackground(String... args) {
            Log.d("Tweet Text", "> " + args[0]);
            String status = args[0];
            try {
                ConfigurationBuilder builder = new ConfigurationBuilder();
                builder.setOAuthConsumerKey(TWITTER_CONSUMER_KEY);
                builder.setOAuthConsumerSecret(TWITTER_CONSUMER_SECRET);
                // Access Token
                String access_token = mSharedPreferences.getString(
                        PREF_KEY_OAUTH_TOKEN, "");
                // Access Token Secret
                String access_token_secret = mSharedPreferences.getString(
                        PREF_KEY_OAUTH_SECRET, "");
                AccessToken accessToken = new AccessToken(access_token,
                        access_token_secret);
                Twitter twitter = new TwitterFactory(builder.build())
                        .getInstance(accessToken);
                // Update status
                twitter4j.Status response = twitter.updateStatus(status);
                Log.d("Status", "> " + response.getText());
            } catch (TwitterException e) {
                // Error in updating status
                Log.d("Twitter Update Error", e.getMessage());
            }
            return null;
        }
        /**
         * After completing background task Dismiss the progress dialog and show
         * the data in UI Always use runOnUiThread(new Runnable()) to update UI
         * from background thread, otherwise you will get error
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),
                            "Tweet posted :D", Toast.LENGTH_SHORT)
                            .show();
                    // Clearing EditText field
                    txtUpdate.setText("");
                }
            });
        }
    }

    public void onClick(View v) {
        if(v == btnUpdateStatus){
            // Call update status function
            // Get the status from EditText
            String status = txtUpdate.getText().toString();
            // Check for blank text
            if (status.trim().length() > 0) {
                // update status
                new updateTwitterStatus().execute(status);
            } else {
                // EditText is empty
                Toast.makeText(getApplicationContext(),
                        "Please enter status message", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
}
