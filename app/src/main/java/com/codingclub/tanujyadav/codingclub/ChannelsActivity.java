package com.codingclub.tanujyadav.codingclub;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class ChannelsActivity extends AppCompatActivity {
ListView ch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channels);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ch=(ListView)findViewById(R.id.chan);

        String[] values =getResources().getStringArray(R.array.channels);


        CustomListAdapter adapter = new CustomListAdapter(ChannelsActivity.this , R.layout.custom_list , values,2);



        ch.setAdapter(adapter);


        ch.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


                int itemPosition = position;

                if (itemPosition == 0) {Uri url = Uri.parse("https://www.youtube.com/user/thenewboston/playlists");
                    Intent facebook = new Intent(Intent.ACTION_VIEW, url);
                    PackageManager packageManager = getPackageManager();
                    List activities = packageManager.queryIntentActivities(facebook,
                            PackageManager.MATCH_DEFAULT_ONLY);
                    boolean isIntentSafe = activities.size() > 0;
                    if (isIntentSafe)
                        startActivity(facebook);
                }

                else if (itemPosition == 1) {
                    Uri url = Uri.parse("https://www.youtube.com/user/xoaxdotnet/playlists");
                    Intent facebook = new Intent(Intent.ACTION_VIEW, url);
                    PackageManager packageManager = getPackageManager();
                    List activities = packageManager.queryIntentActivities(facebook,
                            PackageManager.MATCH_DEFAULT_ONLY);
                    boolean isIntentSafe = activities.size() > 0;
                    if(isIntentSafe)
                        startActivity(facebook);
                }

                else if (itemPosition == 2) {
                    Uri url = Uri.parse("https://www.youtube.com/user/phpacademy/playlists");
                    Intent facebook = new Intent(Intent.ACTION_VIEW, url);
                    PackageManager packageManager = getPackageManager();
                    List activities = packageManager.queryIntentActivities(facebook,
                            PackageManager.MATCH_DEFAULT_ONLY);
                    boolean isIntentSafe = activities.size() > 0;
                    if(isIntentSafe)
                        startActivity(facebook);
                }


            }

        });
    }
    }


