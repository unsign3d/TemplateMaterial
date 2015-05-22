package com.example.unsigned.provamaterial;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;


public class MainActivity extends ActionBarActivity {

    private Toolbar mToolbar;
    private MenuDrawer mDrawerFragment;
    private LoginButton loginButton;           //facebook login
    private CallbackManager callbackManager;   // facebook callback manager
    private View mLoginView;
    private View mMainContent;
    private TextView logout_button;


    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        // This is basically the Facebook tracker
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.

        AppEventsLogger.deactivateApp(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);


        mLoginView = findViewById(R.id.activity_login);
        mMainContent = findViewById(R.id.main_content);
        logout_button = (TextView) findViewById(R.id.logout_button);

        // create the ActionBar
        mToolbar = (Toolbar) findViewById(R.id.action_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // create the menufragment
        mDrawerFragment = (MenuDrawer) getFragmentManager().findFragmentById(R.id.fragment_navigation_bar_impl);
        mDrawerFragment.setUp(R.id.fragment_navigation_bar_impl, (DrawerLayout) findViewById(R.id.main_drawer), mToolbar);


        // facebook login button
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends");

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        changeViewLogged();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(getApplicationContext(),
                                getString(R.string.facebook_onCancel), Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(getApplicationContext(),
                                getString(R.string.facebook_onError), Toast.LENGTH_LONG).show();
                        Log.e(getString(R.string.log_tag), exception.toString());
                    }
                });
        // end facebook

        // if user is logged in show the main content, if not show the main content
        if (AccessToken.getCurrentAccessToken()!=null) {
            changeViewLogged();

            TextView logout_button = (TextView) findViewById(R.id.logout_button);

        }
        else {
            changeViewLoggedOut();


            logout_button.setText("effettua il login");
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void logout(View view){
        Toast.makeText(getApplicationContext(), "ioasdfosaiefj", Toast.LENGTH_LONG).show();

        LoginManager.getInstance().logOut();

        changeViewLoggedOut();

    }

    private void changeViewLogged(){
        mMainContent.setVisibility(View.VISIBLE);
        mLoginView.setVisibility(View.GONE);

        logout_button.setText("LOGOUT");
    }

    private void changeViewLoggedOut() {
        mMainContent.setVisibility(View.GONE);
        mLoginView.setVisibility(View.VISIBLE);

        logout_button.setText("effettua il login");
    }
}
