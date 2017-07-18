package com.codingclub.tanujyadav.codingclub;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
View v;
 TextView q,l;

    public HomeFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.fragment_home, container, false);
        q=(TextView)v.findViewById(R.id.ques);
        l=(TextView)v.findViewById(R.id.leader);
        String url="https://nitd.000webhostapp.com/coding%20club/question.txt";
        String url1="https://nitd.000webhostapp.com/coding%20club/leaderboard.txt";
        new readtextfile(q,url,0).execute();
        new readtextfile(l,url1,1).execute();
        return v;
    }

}