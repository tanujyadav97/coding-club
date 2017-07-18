package com.codingclub.tanujyadav.codingclub;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.ListFragment;
import android.annotation.SuppressLint;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MyListFragment extends ListFragment implements OnItemClickListener {
    static int itemPos;
    public MyListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int id = R.layout.custom_list;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String[] values =getResources().getStringArray(R.array.sites);
        CustomListAdapter adapter = new CustomListAdapter(getActivity(),R.layout.custom_list ,values,1);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,long id) {


        itemPos     = position;
        if(position==8)
        {
            Uri url = Uri.parse("https://developer.android.com/index.html");
            Intent facebook = new Intent(Intent.ACTION_VIEW, url);
            PackageManager packageManager = getActivity().getPackageManager();
            List activities = packageManager.queryIntentActivities(facebook,
                    PackageManager.MATCH_DEFAULT_ONLY);
            boolean isIntentSafe = activities.size() > 0;
            if(isIntentSafe)
                startActivity(facebook);
        }
        else if(position==9)
        {
            Uri url = Uri.parse("https://docs.oracle.com/javase/tutorial/");
            Intent facebook = new Intent(Intent.ACTION_VIEW, url);
            PackageManager packageManager = getActivity().getPackageManager();
            List activities = packageManager.queryIntentActivities(facebook,
                    PackageManager.MATCH_DEFAULT_ONLY);
            boolean isIntentSafe = activities.size() > 0;
            if(isIntentSafe)
                startActivity(facebook);
        }
        else if(position==10)
        {
            Uri url = Uri.parse("https://developer.apple.com/library/ios/referencelibrary/GettingStarted/DevelopiOSAppsSwift/");
            Intent facebook = new Intent(Intent.ACTION_VIEW, url);
            PackageManager packageManager = getActivity().getPackageManager();
            List activities = packageManager.queryIntentActivities(facebook,
                    PackageManager.MATCH_DEFAULT_ONLY);
            boolean isIntentSafe = activities.size() > 0;
            if(isIntentSafe)
                startActivity(facebook);
        }
        else if(position==11)
        {
            Intent intent=new Intent(getActivity(),ChannelsActivity.class);
            startActivity(intent);
        }
        else{
            Intent intent=new Intent(getActivity(),TopicsActivity.class);
            startActivity(intent);
        }
    }
}