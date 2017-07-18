package com.codingclub.tanujyadav.codingclub;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import static com.codingclub.tanujyadav.codingclub.MainActivity.ps;


/**
 * A simple {@link Fragment} subclass.
 */
public class AboutrealFragment extends Fragment {

String url1="https://nitd.000webhostapp.com/coding%20club/team.jpg",url2="https://nitd.000webhostapp.com/coding%20club/aboutus.txt";
View v;

    public AboutrealFragment() {
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
v=inflater.inflate(R.layout.fragment_aboutreal, container, false);
ImageView imv=(ImageView)v.findViewById(R.id.imvvv);
        Glide.with(getActivity()).load(url1)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imv);

        TextView tv=(TextView)v.findViewById(R.id.aboutus);
        if(isNetworkAvailable())
        {
            new readtextfile(tv,url2,2).execute();
        }
        else
        {
            if(!ps.getString("aboutus","n/a").toString().equals("n/a"))
            tv.setText(ps.getString("aboutus","n/a").toString());
            else
            {
                tv.setText("Currently unavailable! Please check your internet connection!");
            }
        }

        return v;
    }


    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}