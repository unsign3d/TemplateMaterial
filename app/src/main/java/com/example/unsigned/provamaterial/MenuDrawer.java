package com.example.unsigned.provamaterial;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MenuDrawer extends Fragment {

    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mDrawerToggle;
    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstance;

    private static final String PREF_FILE_NAME = "com.unsigned.provamaterial";
    private static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";

    private View mContainerView;

    public MenuDrawer(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUserLearnedDrawer =  Boolean.getBoolean(readToPreference(getActivity(), KEY_USER_LEARNED_DRAWER, "false"));

        if (savedInstanceState != null) {
            mFromSavedInstance = true;
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu_drawer, container, false);

    }


    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {

        this.mContainerView = getActivity().findViewById(fragmentId);
        this.mToolbar = toolbar;

        this.mDrawerLayout = drawerLayout;

        // seetup the ActionBarToggle
        this.mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),
                mDrawerLayout,
                mToolbar,
                R.string.drawer_open,
                R.string.drawer_close
        ){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                /*
                if(!mUserLearnedDrawer){
                    mUserLearnedDrawer = true;
                    saveToPreference(getActivity(), KEY_USER_LEARNED_DRAWER, String.valueOf(mUserLearnedDrawer));
                }
                */
                // redraw the actionbar
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

        };

        // if the user has returned from saved instance and
        // the drawer was previously opened we reopened it

        /*
        if(!mUserLearnedDrawer && !mFromSavedInstance) {
            mDrawerLayout.openDrawer(mContainerView);

        }
        */
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        // we need do tell the drawerActivity that we need to redraw
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }


    public static void saveToPreference (Context context, String preferenceName, String preferenceValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply();
    }
    public static String readToPreference (Context context, String preferenceName, String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(preferenceName, defaultValue);
    }
}
