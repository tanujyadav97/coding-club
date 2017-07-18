package com.codingclub.tanujyadav.codingclub;

import android.os.AsyncTask;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import static com.codingclub.tanujyadav.codingclub.MainActivity.pe;
import static com.codingclub.tanujyadav.codingclub.MainActivity.ps;

/**
 * Created by 15121 on 3/13/2017.
 */

public class readtextfile extends AsyncTask<String, Integer, String> {

    String uu;
    int a;

    private TextView textViewToSet;
    public readtextfile(TextView descriptionTextView,String ss,int t){
        this.textViewToSet = descriptionTextView;
        this.uu=ss;
        a=t;
    }

    @Override
    protected String doInBackground(String... params) {
        String result = "";
        try {
            URL url = new URL(uu);

            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String line = null;

            while ((line = in.readLine()) != null) {
                //get lines
                result+=line+"\n";
            }
            in.close();


        } catch (MalformedURLException e) {
            if(a==0)
            result="Unable to fetch question";
            else if(a==1)
                result="Unable to fetch leaderboard";
            else
                result="Currently unavailable! Please check your internet connection!";
            e.printStackTrace();
        }
        catch (IOException e) {
            if(a==0)
                result="Unable to fetch question";
            else if(a==1)
                result="Unable to fetch leaderboard";
            else
               result="Currently unavailable! Please check your internet connection!";
            e.printStackTrace();
        }

        if(a==2&&!result.equals("Currently unavailable! Please check your internet connection!"))
        {
            pe.putString("aboutus",result);
            pe.commit();
        }
        return result;
    }

    protected void onProgressUpdate() {
        //called when the background task makes any progress
    }

    protected void onPreExecute() {
        //called before doInBackground() is started
    }

    @Override
    protected void onPostExecute(String result) {
        this.textViewToSet.setText(result);
    }
}

