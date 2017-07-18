package com.codingclub.tanujyadav.codingclub;


import android.content.Context;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static com.codingclub.tanujyadav.codingclub.AppController.TAG;
import static com.codingclub.tanujyadav.codingclub.MainActivity.flagg1;


/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {

    public static com.codingclub.tanujyadav.codingclub.AboutFragment.MyAppAdapter myAppAdapter;
    public static com.codingclub.tanujyadav.codingclub.AboutFragment.ViewHolder viewHolder;
    private ArrayList<Data> array=new ArrayList<>();
    private SwipeFlingAdapterView flingContainer;
    View view;
    private String URL_FEED = "https://nitd.000webhostapp.com/coding%20club/achievements.json";


    public AboutFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_about, container, false);


        flingContainer = (SwipeFlingAdapterView) view.findViewById(R.id.frame);

        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(URL_FEED);
        if (flagg1==1&&entry != null) {
            // fetch the data from cache
            try {
                String data = new String(entry.data, "UTF-8");
                try {
                    parseJsonFeed(new JSONObject(data));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        } else {
            flagg1++;
            // making fresh volley request and getting json
            JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
                    URL_FEED, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    VolleyLog.d(TAG, "Response: " + response.toString());
                    if (response != null) {
                        parseJsonFeed(response);
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                }
            });

            // Adding request to volley request queue
            AppController.getInstance().addToRequestQueue(jsonReq);
        }


        myAppAdapter = new com.codingclub.tanujyadav.codingclub.AboutFragment.MyAppAdapter(array, getActivity());

        flingContainer.setAdapter(myAppAdapter);
        //myAppAdapter.notifyDataSetChanged();
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {

            }

            @Override
            public void onLeftCardExit(Object dataObject) {

                Data d=array.get(0);
                array.remove(0);
                array.add(d);
                myAppAdapter.notifyDataSetChanged();
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
            }

            @Override
            public void onRightCardExit(Object dataObject) {

                Data d=array.get(0);
                array.remove(0);
                array.add(d);
                myAppAdapter.notifyDataSetChanged();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {

            }

            @Override
            public void onScroll(float scrollProgressPercent) {

                View view = flingContainer.getSelectedView();
                view.findViewById(R.id.background).setAlpha(0);
                view.findViewById(R.id.item_swipe_right_indicator).setAlpha(0);
                view.findViewById(R.id.item_swipe_left_indicator).setAlpha(0);
            }
        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {

                View view = flingContainer.getSelectedView();
                view.findViewById(R.id.background).setAlpha(0);

                myAppAdapter.notifyDataSetChanged();
            }
        });


        return view;
    }



    private void parseJsonFeed(JSONObject response) {
        try {
            JSONArray feedArray = response.getJSONArray("feed");
          //  FrameLayout layout1 = (FrameLayout) View.inflate(getActivity(), R.layout.item, null);
            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);

                String image = feedObj.isNull("image") ? null : feedObj
                        .getString("image");

                array.add(new Data(feedObj.getString("name"),image,feedObj.getString("description")));


            }

            // notify data changes to list adapater

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public static class ViewHolder {
        public static FrameLayout background;
        public TextView DataText;
        public TextView DataText1;
        public ImageView cardImage;


    }

    public class MyAppAdapter extends BaseAdapter {


        public List<Data> parkingList;
        public Context context;

        private MyAppAdapter(List<Data> apps, Context context) {
            this.parkingList = apps;
            this.context = context;
        }

        @Override
        public int getCount() {
            return parkingList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View rowView = convertView;


            if (rowView == null) {

                LayoutInflater inflater = getActivity().getLayoutInflater();
                rowView = inflater.inflate(R.layout.item, parent, false);
                // configure view holder
                viewHolder = new com.codingclub.tanujyadav.codingclub.AboutFragment.ViewHolder();
                viewHolder.DataText1 = (TextView) rowView.findViewById(R.id.bookText1);
                viewHolder.DataText = (TextView) rowView.findViewById(R.id.bookText);
                viewHolder.background = (FrameLayout) rowView.findViewById(R.id.background);
                viewHolder.cardImage = (ImageView) rowView.findViewById(R.id.cardImage);
                rowView.setTag(viewHolder);

            } else {
                viewHolder = (com.codingclub.tanujyadav.codingclub.AboutFragment.ViewHolder) convertView.getTag();
            }
            viewHolder.DataText1.setText(parkingList.get(position).getheading() + "");

            viewHolder.DataText.setText(parkingList.get(position).getDescription() + "");
            Glide.with(getActivity()).load(parkingList.get(position).getImagePath()).into(viewHolder.cardImage);

            String s=parkingList.get(position).getImagePath();

          //  Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();

            if(s==null)
            {
                viewHolder.cardImage.setVisibility(View.GONE);
            }
            else {
                viewHolder.cardImage.setVisibility(View.VISIBLE);
                }


            return rowView;
        }
    }

}