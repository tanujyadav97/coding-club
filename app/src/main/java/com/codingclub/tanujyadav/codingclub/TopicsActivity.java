package com.codingclub.tanujyadav.codingclub;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class TopicsActivity extends AppCompatActivity {
   String button;
    ListView lv;
    String[] topics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        int id = R.layout.custom_list;

        lv=(ListView)findViewById(R.id.lv);

        if(MyListFragment.itemPos==0)
        {  topics= getResources().getStringArray(R.array.topic1);
            button="http://www.tutorialspoint.com/tutorialslibrary.htm";
        }
        else if(MyListFragment.itemPos==1)
        {topics= getResources().getStringArray(R.array.topic2);
            button="https://www.khanacademy.org/computing/computer-science/";
        }
        else if(MyListFragment.itemPos==2)
        {topics= getResources().getStringArray(R.array.topic3);
            button="https://www.coursera.org/featured/top_specializations_locale_en_os_web_14d";
        }
        else if(MyListFragment.itemPos==3)
        {topics= getResources().getStringArray(R.array.topic4);
            button="http://www.techtutorials.net/";
        }
        else if(MyListFragment.itemPos==4)
        {topics= getResources().getStringArray(R.array.topic5);
            button="http://www.cprogramming.com/tutorial.html";
        }
        else if(MyListFragment.itemPos==5)
        {topics= getResources().getStringArray(R.array.topic6);
            button="http://htmldog.com/";
        }
        else if(MyListFragment.itemPos==6)
        {topics= getResources().getStringArray(R.array.topic7);
            button="http://www.python-course.eu/";
        }
        else if(MyListFragment.itemPos==7)
        {topics= getResources().getStringArray(R.array.topic8);
            button="http://www.aihorizon.com/";
        }


        CustomListAdapter adapter = new CustomListAdapter(TopicsActivity.this , R.layout.custom_list , topics,2);


        lv.setAdapter(adapter);




    }



    public void gotosite(View view)
    { Uri url = Uri.parse(button);
        Intent facebook = new Intent(Intent.ACTION_VIEW, url);
        PackageManager packageManager = getPackageManager();
        List activities = packageManager.queryIntentActivities(facebook,
                PackageManager.MATCH_DEFAULT_ONLY);
        boolean isIntentSafe = activities.size() > 0;
        if(isIntentSafe)
            startActivity(facebook);}
}
